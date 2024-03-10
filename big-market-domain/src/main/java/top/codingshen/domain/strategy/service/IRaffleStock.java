package top.codingshen.domain.strategy.service;

import top.codingshen.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

/**
 * @ClassName IRaffleStock
 * @Description 愁啊经库存相关服务, 获取库存消耗队列
 * @Author alex_shen
 * @Date 2024/3/9 - 21:39
 */
public interface IRaffleStock {

    /**
     * 获取奖品库存消耗队列
     *
     * @return 奖品库存 key 信息
     */
    StrategyAwardStockKeyVO takeQueueValue();

    /**
     * 更新奖品库存消耗记录
     *
     * @param strategyId 策略 id
     * @param awardId    奖品 id
     */
    void updateStrategyAwardStock(Long strategyId, Integer awardId);
}
