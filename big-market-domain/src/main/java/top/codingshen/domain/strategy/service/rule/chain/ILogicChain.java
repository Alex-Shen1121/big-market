package top.codingshen.domain.strategy.service.rule.chain;

import top.codingshen.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

/**
 * @ClassName ILogicChain
 * @Description 责任链接口
 * @Author alex_shen
 * @Date 2024/3/6 - 22:51
 */
public interface ILogicChain extends ILogicChainArmory, Cloneable {

    /**
     * 责任链接口
     *
     * @param userId     用户 id
     * @param strategyId 策略 id
     * @return 奖品 id
     */
    DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId);
}
