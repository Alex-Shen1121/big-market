package top.codingshen.domain.strategy.service.rule.tree.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.codingshen.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import top.codingshen.domain.strategy.service.rule.tree.ILogicTreeNode;
import top.codingshen.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import top.codingshen.types.common.Constants;

/**
 * @ClassName RuleLuckAwardLogicTreeNode
 * @Description 兜底奖励节点
 * @Author alex_shen
 * @Date 2024/3/7 - 15:53
 */
@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        // eg: 101:1,100
        log.info("规则过滤 - 兜底奖品 userId:{}, strategyId:{}, awardId:{}, ruleValue:{}", userId, strategyId, awardId, ruleValue);
        String[] split = ruleValue.split(Constants.COLON);
        if (split.length == 0) {
            log.error("规则过滤 - 兜底奖品, 兜底奖品未配置告警 userId:{}, strategyId:{}, awardId:{}", userId, strategyId, awardId);
            throw new RuntimeException("兜底奖品未配置" + ruleValue);
        }

        // 兜底奖品配置
        Integer luckAwardId = Integer.valueOf(split[0]);
        String awardRuleValue = split.length > 1 ? split[1] : "";
        log.info("规则过滤 - 兜底奖品 userId:{}, strategyId:{}, awardId:{}, awardRuleValue:{}", userId, strategyId, luckAwardId, awardRuleValue);

        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                        .awardId(luckAwardId)
                        .awardRuleValue(awardRuleValue)
                        .build())
                .build();
    }
}
