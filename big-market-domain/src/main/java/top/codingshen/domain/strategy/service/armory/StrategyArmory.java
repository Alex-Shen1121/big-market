package top.codingshen.domain.strategy.service.armory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.codingshen.domain.strategy.model.entity.StrategyAwardEntity;
import top.codingshen.domain.strategy.repository.IStrategyRepository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * @ClassName StrategyArmory
 * @Description 策略装配库(兵工厂), 负责初始化策略计算
 * @Author alex_shen
 * @Date 2024/3/4 - 15:05
 */
@Slf4j
@Service
public class StrategyArmory implements IStrategyArmory {

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

        // 2. 获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        // 3. 获取概率值的总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. 利用 总和/最小值 获取概率范围, 百分位/千分位/万分位
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        // todo: 计算有 bug, 会爆空间
        // 5. 生成策略奖品概率查找表「这里指需要在list集合中，存放上对应的奖品占位即可，占位越多等于概率越高」
        ArrayList<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAward : strategyAwardEntities) {
            Integer awardId = strategyAward.getAwardId();
            BigDecimal awardRate = strategyAward.getAwardRate();

            // 计算出每个概率值需要存放到查找表的数量，循环填充
            // eg. rateRange 概率范围 = [0, 1000]
            // awardRate = 80%, 则 rateRange * awardRate = 1000 * 0.8 = 800 的范围都是对应这个策略
            for (int i = 0; i < rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i++) {
                strategyAwardSearchRateTables.add(awardId);
            }
        }

        // 6. 对存储的奖品进行乱序操作
        Collections.shuffle(strategyAwardSearchRateTables);

        // 7. 生成抽奖 Map 集合, key = rateRange 范围内的数, value = 对应的 award
        Map<Integer, Integer> shuffleStrategyAwardSearchRateTables = new HashMap<>();
        for (int i = 0; i < strategyAwardSearchRateTables.size(); i++) {
            shuffleStrategyAwardSearchRateTables.put(i, strategyAwardSearchRateTables.get(i));
        }

        // 8. 将策略奖品查询概率表存放到 Redis 中
        repository.storeStrategyAwardSearchRateTable(strategyId, shuffleStrategyAwardSearchRateTables.size(), shuffleStrategyAwardSearchRateTables);

        return true;
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange = repository.getRateRange(strategyId);
        return repository.getStrategyAwardAssemble(strategyId, new SecureRandom().nextInt(rateRange));
    }
}
