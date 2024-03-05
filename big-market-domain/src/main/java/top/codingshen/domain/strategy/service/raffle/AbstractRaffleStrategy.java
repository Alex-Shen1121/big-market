package top.codingshen.domain.strategy.service.raffle;

import top.codingshen.domain.strategy.model.entity.RaffleAwardEntity;
import top.codingshen.domain.strategy.model.entity.RaffleFactorEntity;
import top.codingshen.domain.strategy.service.IRaffleStrategy;

/**
 * @ClassName AbstractRaffleStrategy
 * @Description 抽奖策略抽象类
 * @Author alex_shen
 * @Date 2024/3/5 - 14:06
 */
public class AbstractRaffleStrategy implements IRaffleStrategy {
    /**
     * 执行抽奖；用抽奖因子入参，执行抽奖计算，返回奖品信息
     *
     * @param raffleFactorEntity 抽奖因子实体对象，根据入参信息计算抽奖结果
     * @return 抽奖的奖品
     */
    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {

        return null;
    }
}
