package top.codingshen.domain.strategy.service.armory;

/**
 * @ClassName IStrategyDispatch
 * @Description 策略抽奖调度
 * @Author alex_shen
 * @Date 2024/3/4 - 20:41
 */
public interface IStrategyDispatch {

    /**
     * 根据策略 id 进行随机抽奖
     *
     * @param strategyId 策略 id
     * @return 奖品 id
     */
    public Integer getRandomAwardId(Long strategyId);

    /**
     * 根据策略 id + 权重值 进行随机抽奖
     *
     * @param strategyId      策略 id
     * @param ruleWeightValue 权重值
     * @return 奖品 id
     */
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue);

    /**
     * 根据策略 id 和奖品 id, 扣减奖品缓存库存
     *
     * @param strategyId 策略 id
     * @param awardId    奖品 id
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(Long strategyId, Integer awardId);
}
