package top.codingshen.domain.strategy.service.armory;

/**
 * @ClassName IStrategyArmory
 * @Description 策略装配库(兵工厂), 负责初始化策略计算
 * @Author alex_shen
 * @Date 2024/3/4 - 15:02
 */
public interface IStrategyArmory {

    /**
     * 根据策略 id 选择抽奖策略进行装配
     *
     * @param strategyId 策略 id
     */
    boolean assembleLotteryStrategy(Long strategyId);

}
