package top.codingshen.domain.strategy.service.rule.tree.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.codingshen.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import top.codingshen.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import top.codingshen.domain.strategy.repository.IStrategyRepository;
import top.codingshen.domain.strategy.service.armory.IStrategyDispatch;
import top.codingshen.domain.strategy.service.rule.tree.ILogicTreeNode;
import top.codingshen.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

import javax.annotation.Resource;

/**
 * @ClassName RuleStockLogicTreeNode
 * @Description 库存节点
 * @Author alex_shen
 * @Date 2024/3/7 - 15:54
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {
    @Resource
    private IStrategyDispatch strategyDispatch;

    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则过滤 - 库存扣减 userId:{}, strategyId:{}, awardId:{}", userId, strategyId, awardId);
        // 扣减库存
        Boolean success = strategyDispatch.subtractionAwardStock(strategyId, awardId);

        // 扣减成功
        if (success) {
            log.info("规则过滤 - 库存扣减成功 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);

            // 写入延迟队列, 延迟消费更新数据库记录
            // [在 trigger 的 job: UpdateAwardStockJob 下消费队列, 更新数据库记录]
            strategyRepository.awardStockConsumeSendQueue(StrategyAwardStockKeyVO.builder()
                    .awardId(awardId)
                    .strategyId(strategyId)
                    .build());

            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                    .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                            .awardId(awardId)
                            .awardRuleValue("")
                            .build())
                    .build();
        }


        // 如果库存不足，则直接返回放行
        log.warn("规则过滤 - 库存扣减告警，库存不足。userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }
}
