package top.codingshen.infrastructure.persistent.repository;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.springframework.stereotype.Service;
import top.codingshen.domain.strategy.model.entity.StrategyAwardEntity;
import top.codingshen.domain.strategy.repository.IStrategyRepository;
import top.codingshen.infrastructure.persistent.dao.IStrategyAwardDao;
import top.codingshen.infrastructure.persistent.po.StrategyAward;
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
    private IStrategyAwardDao strategyAwardDao;

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
        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);

        strategyAwardEntities = new ArrayList<>(strategyAwards.size());
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder().strategyId(strategyAward.getStrategyId()).awardId(strategyAward.getAwardId()).awardCount(strategyAward.getAwardCount()).awardCountSurplus(strategyAward.getAwardCountSurplus()).awardRate(strategyAward.getAwardRate()).build();
            strategyAwardEntities.add(strategyAwardEntity);
        }

        redisService.setValue(cacheKey, strategyAwardEntities);
        return strategyAwardEntities;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(Long strategyId, Integer rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTables) {
        // 1. 存储抽奖策略范围值, 如 10000, 用于生成 10000 以内的随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId, rateRange);
        // 2. 存储概率查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId);
        cacheRateTable.putAll(shuffleStrategyAwardSearchRateTables);
    }

    @Override
    public int getRateRange(Long strategyId) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId);
    }

    @Override
    public Integer getStrategyAwardAssemble(Long strategyId, int rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId, rateKey);
    }
}
