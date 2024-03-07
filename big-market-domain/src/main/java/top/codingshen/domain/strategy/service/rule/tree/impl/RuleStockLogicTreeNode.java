package top.codingshen.domain.strategy.service.rule.tree.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.codingshen.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import top.codingshen.domain.strategy.service.rule.tree.ILogicTreeNode;
import top.codingshen.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @ClassName RuleStockLogicTreeNode
 * @Description 库存节点
 * @Author alex_shen
 * @Date 2024/3/7 - 15:54
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }
}
