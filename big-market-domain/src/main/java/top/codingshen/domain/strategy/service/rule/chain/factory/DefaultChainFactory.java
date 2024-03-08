package top.codingshen.domain.strategy.service.rule.chain.factory;

import lombok.*;
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


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /**  */
        private String logicModel;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;

    }

}
