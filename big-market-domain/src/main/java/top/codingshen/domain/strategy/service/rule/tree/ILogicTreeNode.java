package top.codingshen.domain.strategy.service.rule.tree;

import top.codingshen.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @ClassName ILogicTreeNode
 * @Description 规则树接口
 * @Author alex_shen
 * @Date 2024/3/7 - 15:44
 */
public interface ILogicTreeNode {

    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId);
}
