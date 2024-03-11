package top.codingshen.domain.strategy.service;

import top.codingshen.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @ClassName IRaffleAward
 * @Description 策略奖品接口
 * @Author alex_shen
 * @Date 2024/3/10 - 21:49
 */
public interface IRaffleAward {

    /**
     * 根据策略ID查询抽奖奖品列表配置
     *
     * @param strategyId 策略ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);
}
