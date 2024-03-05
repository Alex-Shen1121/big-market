package top.codingshen.domain.strategy.service;

import top.codingshen.domain.strategy.model.entity.RaffleAwardEntity;
import top.codingshen.domain.strategy.model.entity.RaffleFactorEntity;
import top.codingshen.domain.strategy.model.entity.RuleActionEntity;

/**
 * @ClassName IRaffleStrategy
 * @Description 抽奖策略接口
 * @Author alex_shen
 * @Date 2024/3/5 - 14:01
 */
public interface IRaffleStrategy {

    /**
     * 执行抽奖；用抽奖因子入参，执行抽奖计算，返回奖品信息
     * @param raffleFactorEntity 抽奖因子实体对象，根据入参信息计算抽奖结果
     * @return 抽奖的奖品
     */
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);

}
