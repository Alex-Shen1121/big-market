package top.codingshen.domain.strategy.service.armory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.codingshen.domain.strategy.model.entity.StrategyAwardEntity;
import top.codingshen.domain.strategy.model.entity.StrategyEntity;
import top.codingshen.domain.strategy.model.entity.StrategyRuleEntity;
import top.codingshen.domain.strategy.repository.IStrategyRepository;
import top.codingshen.types.common.Constants;
import top.codingshen.types.enums.ResponseCode;
import top.codingshen.types.exception.AppException;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * @ClassName StrategyArmoryDispatch
 * @Description 策略装配库(兵工厂), 负责初始化策略计算
 * @Author alex_shen
 * @Date 2024/3/4 - 15:05
 */
@Slf4j
@Service
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch {

    @Resource
    private IStrategyRepository repository;

    /**
     * 根据 strategyId 选择抽奖策略进行装配
     *
     * @param strategyId
     */
    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        // 1. 查询策略配置
        List<StrategyAwardEntity> strategyAwardEntities = repository.queryStrategyAwardList(strategyId);

        // 2. 缓存奖品库存[用于 decr 扣减库存使用]
        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntities) {
            Integer awardId = strategyAwardEntity.getAwardId();
            Integer awardCount = strategyAwardEntity.getAwardCount();
            cacheStrategyAwardCount(strategyId, awardId, awardCount);
        }

        // 3.1 默认装配配置
        // 全量抽奖概率
        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);

        // 3.2. 权重策略配置
        // 适用于 rule_weight 权重规则配置
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);
        String ruleWeight = strategyEntity.getRuleWeight();
        // 检测 strategy_id 策略是否配置了rule_weight 规则
        if (null == ruleWeight) return true;

        // 查询该策略下 rule_weight 具体策略规则
        StrategyRuleEntity strategyRuleEntity = repository.queryStrategyRule(strategyId, ruleWeight);
        if (strategyRuleEntity == null) {
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(), ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }
        Map<String, List<Integer>> ruleWeightValueMap = strategyRuleEntity.getRuleWeightValues();
        Set<String> keys = ruleWeightValueMap.keySet();
        // key -> 4000:102,103,104,105
        // value -> [102,103,104,105]
        for (String key : keys) {
            List<Integer> ruleWeightValues = ruleWeightValueMap.get(key);
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
            strategyAwardEntitiesClone.removeIf(entity -> !ruleWeightValues.contains(entity.getAwardId()));
            assembleLotteryStrategy(String.valueOf(strategyId).concat(Constants.COLON).concat(key), strategyAwardEntitiesClone);
        }

        return true;
    }

    private void cacheStrategyAwardCount(Long strategyId, Integer awardId, Integer awardCount) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + strategyId + Constants.COLON + awardId;
        repository.cacheStrategyAwardCount(cacheKey, awardCount);
    }

    private void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntities) {

        // 1. 获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntities.stream().map(StrategyAwardEntity::getAwardRate).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO).stripTrailingZeros();

        // 2. 获取概率值的总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream().map(StrategyAwardEntity::getAwardRate).reduce(BigDecimal.ZERO, BigDecimal::add);

        int powerOfTen = findPowerOfTenForConversion(minAwardRate);

        // 6. 生成抽奖 Map 集合, key = rateRange 范围内的数, value = 对应的 award
        Map<String, Integer> shuffleStrategyAwardSearchRateTables = new HashMap<>();

        int probability = 0;

        // 5. 对存储的奖品进行乱序操作
        Collections.shuffle(strategyAwardEntities);

        for (StrategyAwardEntity strategyAward : strategyAwardEntities) {
            Integer awardId = strategyAward.getAwardId();
            BigDecimal awardRate = strategyAward.getAwardRate();

            int processedAwardRate = awardRate.multiply(BigDecimal.valueOf(powerOfTen)).intValue();

            shuffleStrategyAwardSearchRateTables.put(String.valueOf(probability) + Constants.SPLIT + String.valueOf(probability + processedAwardRate), awardId);

            probability += processedAwardRate;
        }

        // 7. 将策略奖品查询概率表存放到 Redis 中
        repository.storeStrategyAwardSearchRateTable(key, totalAwardRate.multiply(BigDecimal.valueOf(powerOfTen)).intValue(), shuffleStrategyAwardSearchRateTables);
    }

    private int findPowerOfTenForConversion(BigDecimal minAwardRate) {
        int maxDecimalPlaces = 0;

        String numAsString = String.valueOf(minAwardRate);
        int decimalPlaces = numAsString.length() - numAsString.indexOf('.') - 1;
        maxDecimalPlaces = Math.max(maxDecimalPlaces, decimalPlaces);

        return (int) Math.pow(10, maxDecimalPlaces);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        // 分布式部署下, 不一定为当前应用做的策略装配, 也就是值不一定保存到本应用, 而是分布式应用, 所以需要从 redis 中获取
        int rateRange = repository.getRateRange(strategyId);
        Map<String, Integer> shuffleStrategyAwardSearchRateTables = repository.getStrategyAwardRateTable(strategyId);
        int randomNumber = new SecureRandom().nextInt(rateRange);
        for (String key : shuffleStrategyAwardSearchRateTables.keySet()) {
            String[] range = key.split(Constants.SPLIT);
            if (randomNumber >= Integer.parseInt(range[0]) && randomNumber < Integer.parseInt(range[1])) {
                return shuffleStrategyAwardSearchRateTables.get(key);
            }
        }
        // 通过生成的随机值, 获取概率值奖品查找表的结果
        throw new RuntimeException("抽奖策额装配错误, 请重新装配");
    }

    /**
     * 根据策略 id + 权重值 进行随机抽奖
     *
     * @param strategyId      策略 id
     * @param ruleWeightValue 权重值
     * @return 奖品 id
     */
    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key = String.valueOf(strategyId).concat(Constants.COLON).concat(ruleWeightValue);
        // 分布式部署下, 不一定为当前应用做的策略装配, 也就是值不一定保存到本应用, 而是分布式应用, 所以需要从 redis 中获取
        int rateRange = repository.getRateRange(key);
        // 通过生成的随机值, 获取概率值奖品查找表的结果
        return repository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));

    }

    @Override
    public Boolean subtractionAwardStock(Long strategyId, Integer awardId) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + strategyId + Constants.COLON + awardId;
        return repository.subtractionAwardStock(cacheKey);
    }
}
