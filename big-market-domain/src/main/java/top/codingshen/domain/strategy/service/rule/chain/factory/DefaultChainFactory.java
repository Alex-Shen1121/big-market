package top.codingshen.domain.strategy.service.rule.chain.factory;

import org.springframework.stereotype.Service;
import top.codingshen.domain.strategy.model.entity.StrategyEntity;
import top.codingshen.domain.strategy.repository.IStrategyRepository;
import top.codingshen.domain.strategy.service.rule.chain.ILogicChain;

import java.util.Map;

/**
 * @ClassName DefaultChainFactory
 * @Description 默认责任链工厂
 * @Author alex_shen
 * @Date 2024/3/6 - 23:51
 */
@Service
public class DefaultChainFactory {

    private final Map<String, ILogicChain> logicChainGroup;

    private final IStrategyRepository repository;

    public DefaultChainFactory(Map<String, ILogicChain> logicChainGroup, IStrategyRepository repository) {
        this.logicChainGroup = logicChainGroup;
        this.repository = repository;
    }

    public ILogicChain openLogicChain(Long strategyId) {
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategy.ruleModels();

        if(null == ruleModels || 0 == ruleModels.length) {
            return logicChainGroup.get("default");
        }

        // 生成责任链
        ILogicChain logicChain = logicChainGroup.get(ruleModels[0]);
        ILogicChain current = logicChain;
        for (String ruleModel : ruleModels) {
            ILogicChain nextChain = logicChainGroup.get(ruleModel);
            current = current.appendNext(nextChain);
        }
        current.appendNext(logicChainGroup.get("default"));

        return logicChain;
    }
}
