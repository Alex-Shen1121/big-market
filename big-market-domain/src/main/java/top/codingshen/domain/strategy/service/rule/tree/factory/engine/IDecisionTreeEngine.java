package top.codingshen.domain.strategy.service.rule.tree.factory.engine;

import top.codingshen.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @ClassName IDecisionTreeEngine
 * @Description 规则书组合接口
 * @Author alex_shen
 * @Date 2024/3/7 - 16:03
 */
public interface IDecisionTreeEngine {

    DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId);

}
