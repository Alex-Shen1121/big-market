package top.codingshen.domain.strategy.service.rule.tree.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.codingshen.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import top.codingshen.domain.strategy.service.rule.tree.ILogicTreeNode;
import top.codingshen.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @ClassName RuleLockLogicTreeNode
 * @Description 次数锁节点
 * @Author alex_shen
 * @Date 2024/3/7 - 15:46
 */
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }
}
