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

    // 用户抽奖次数
    // todo: 从数据库/redis 中读取
    private Long userRaffleCount = 10L;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则过滤 - 次数锁 userId:{}, strategyId:{}, awardId:{}", userId, strategyId, awardId);

        long raffleCount = 0L;
        try {
            raffleCount = Long.parseLong(ruleValue);
        } catch (Exception e) {
            throw new RuntimeException("规则过滤 - 次数锁异常 ruleValue:" + ruleValue + "配置错误.");
        }

        // 用户抽奖次数大于规则限定值, 规则放行
        if (userRaffleCount >= raffleCount) {
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                    .build();
        }

        // 用户抽奖次数小于规则限定值, 规则拦截
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();

    }
}
