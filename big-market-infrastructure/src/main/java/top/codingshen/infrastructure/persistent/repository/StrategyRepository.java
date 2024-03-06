package top.codingshen.infrastructure.persistent.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.codingshen.domain.strategy.model.entity.StrategyAwardEntity;
import top.codingshen.domain.strategy.model.entity.StrategyEntity;
import top.codingshen.domain.strategy.model.entity.StrategyRuleEntity;
import top.codingshen.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import top.codingshen.domain.strategy.repository.IStrategyRepository;
import top.codingshen.infrastructure.persistent.dao.IStrategyAwardDao;
import top.codingshen.infrastructure.persistent.dao.IStrategyDao;
import top.codingshen.infrastructure.persistent.dao.IStrategyRuleDao;
import top.codingshen.infrastructure.persistent.po.StrategyAwardPO;
import top.codingshen.infrastructure.persistent.po.StrategyPO;
import top.codingshen.infrastructure.persistent.po.StrategyRulePO;
import top.codingshen.infrastructure.persistent.redis.IRedisService;
import top.codingshen.types.common.Constants;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName StrategyRepository
 * @Description 策略仓储实现
 * @Author alex_shen
 * @Date 2024/3/4 - 15:18
 */
@Slf4j
@Service
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Resource
    private IStrategyRuleDao strategyRuleDao;

    @Resource
    private IRedisService redisService;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;

        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);

        // redis 中存在缓存
        if (null != strategyAwardEntities && !strategyAwardEntities.isEmpty()) {
            return strategyAwardEntities;
        }

        // redis 中不存在缓存
        // 查询 mysql 数据库
        List<StrategyAwardPO> strategyAwards = strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);

        strategyAwardEntities = new ArrayList<>(strategyAwards.size());
        for (StrategyAwardPO strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder().strategyId(strategyAward.getStrategyId()).awardId(strategyAward.getAwardId()).awardCount(strategyAward.getAwardCount()).awardCountSurplus(strategyAward.getAwardCountSurplus()).awardRate(strategyAward.getAwardRate()).build();
            strategyAwardEntities.add(strategyAwardEntity);
        }

        redisService.setValue(cacheKey, strategyAwardEntities);
        return strategyAwardEntities;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTables) {
        // 1. 存储抽奖策略范围值, 如 10000, 用于生成 10000 以内的随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key, rateRange);
        // 2. 存储概率查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key);
        cacheRateTable.putAll(shuffleStrategyAwardSearchRateTables);
    }

    @Override
    public int getRateRange(Long strategyId) {
        return getRateRange(String.valueOf(strategyId));
    }

    @Override
    public int getRateRange(String key) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key);
    }

    @Override
    public Integer getStrategyAwardAssemble(Long strategyId, int rateKey) {
        return getStrategyAwardAssemble(String.valueOf(strategyId), rateKey);
    }

    @Override
    public Integer getStrategyAwardAssemble(String key, int rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key, rateKey);
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        // 优先从缓存中获取
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);

        // 命中缓存
        if (null != strategyEntity){
            return strategyEntity;
        }

        // 缓存未命中, 从数据库查询
        StrategyPO strategyPO = strategyDao.queryStrategyByStrategyId(strategyId);
        strategyEntity = StrategyEntity.builder().strategyId(strategyPO.getStrategyId()).strategyDesc(strategyPO.getStrategyDesc()).ruleModels(strategyPO.getRuleModels()).build();

        // 存储到缓存
        redisService.setValue(cacheKey, strategyEntity);
        return strategyEntity;
    }

    /**
     * 根据策略 id 和规则模型查询具体的策略规则
     *
     * @param strategyId 策略 id
     * @param ruleModel  规则模型
     * @return 策略规则
     */
    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        StrategyRulePO strategyRuleReq = new StrategyRulePO();
        strategyRuleReq.setStrategyId(strategyId);
        strategyRuleReq.setRuleModel(ruleModel);
        StrategyRulePO strategyRuleRes = strategyRuleDao.queryStrategyRule(strategyRuleReq);
        return StrategyRuleEntity.builder().strategyId(strategyRuleRes.getStrategyId()).awardId(strategyRuleRes.getAwardId()).ruleType(strategyRuleRes.getRuleType()).ruleModel(strategyRuleRes.getRuleModel()).ruleValue(strategyRuleRes.getRuleValue()).ruleDesc(strategyRuleRes.getRuleDesc()).build();
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel) {
        StrategyRulePO strategyRule = new StrategyRulePO();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setAwardId(awardId);
        strategyRule.setRuleModel(ruleModel);
        return strategyRuleDao.queryStrategyRuleValue(strategyRule);
    }

    @Override
    public StrategyAwardRuleModelVO queryStrategyRuleModel(Long strategyId, Integer awardId) {
        StrategyAwardPO strategyAwardPO = new StrategyAwardPO();
        strategyAwardPO.setStrategyId(strategyId);
        strategyAwardPO.setAwardId(awardId);
        String ruleModels =  strategyAwardDao.queryStrategyAwardRuleModels(strategyAwardPO);
        return StrategyAwardRuleModelVO.builder()
                .ruleModels(ruleModels)
                .build();
    }
}
