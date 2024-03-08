package top.codingshen.domain.strategy.service.rule.chain.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.codingshen.domain.strategy.service.armory.IStrategyDispatch;
import top.codingshen.domain.strategy.service.rule.chain.AbstractLogicChain;
import top.codingshen.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

import javax.annotation.Resource;

/**
 * @ClassName DefaultLogicChain
 * @Description 兜底责任链方法
 * @Author alex_shen
 * @Date 2024/3/6 - 23:12
 */
@Slf4j
@Component("default")
public class DefaultLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyDispatch strategyDispatch;

    /**
     * 责任链接口
     *
     * @param userId     用户 id
     * @param strategyId 策略 id
     * @return 奖品 id
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        log.info("抽奖责任链 - 默认处理 userId:{}, ruleModel:{}, awardId:{}", userId, ruleModel(), awardId);
        return DefaultChainFactory.StrategyAwardVO.builder()
                .awardId(awardId)
                .logicModel(ruleModel())
                .build();
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode();
    }
}
