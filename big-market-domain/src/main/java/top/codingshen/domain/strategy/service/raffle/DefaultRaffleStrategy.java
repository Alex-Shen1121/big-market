package top.codingshen.domain.strategy.service.raffle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.codingshen.domain.strategy.model.valobj.RuleTreeVO;
import top.codingshen.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import top.codingshen.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import top.codingshen.domain.strategy.repository.IStrategyRepository;
import top.codingshen.domain.strategy.service.AbstractRaffleStrategy;
import top.codingshen.domain.strategy.service.armory.IStrategyDispatch;
import top.codingshen.domain.strategy.service.rule.chain.ILogicChain;
import top.codingshen.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import top.codingshen.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import top.codingshen.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import top.codingshen.types.common.Constants;

/**
 * @ClassName DefaultRaffleStrategy
 * @Description 默认抽奖策略
 * @Author alex_shen
 * @Date 2024/3/5 - 16:46
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {

    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        super(repository, strategyDispatch, defaultChainFactory, defaultTreeFactory);
    }

    /**
     * 抽奖计算，责任链抽象方法
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId) {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        return logicChain.logic(userId, strategyId);
    }

    /**
     * 抽奖结果过滤，决策树抽象方法
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 过滤结果【奖品ID，会根据抽奖次数判断、库存判断、兜底兜里返回最终的可获得奖品信息】
     */
    @Override
    public DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId) {
        // 查询奖品的规则模型
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);
        if (null == strategyAwardRuleModelVO) {
            return DefaultTreeFactory.StrategyAwardVO.builder()
                    .awardId(awardId)
                    .build();
        }

        // 查询规则树
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId(strategyAwardRuleModelVO.getRuleModels());
        if (ruleTreeVO == null) {
            throw new RuntimeException("存在抽奖策略配置的规则模型 Key, 未在库表 rule_tree, rule_tree_node, rule_tree_line 配置对应的规则树信息");
        }

        IDecisionTreeEngine treeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);
        return treeEngine.process(userId, strategyId, awardId);
    }

    @Override
    public StrategyAwardStockKeyVO takeQueueValue() {
        return repository.takeQueueValue();
    }

    @Override
    public void updateStrategyAwardStock(Long strategyId, Integer awardId) {
        repository.updateStrategyAwardStock(strategyId, awardId);
    }
}
