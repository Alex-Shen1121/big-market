package top.codingshen.domain.strategy.service.rule.chain.factory;

import lombok.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import top.codingshen.domain.strategy.model.entity.StrategyEntity;
import top.codingshen.domain.strategy.repository.IStrategyRepository;
import top.codingshen.domain.strategy.service.rule.chain.ILogicChain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName DefaultChainFactory
 * @Description 默认责任链工厂
 * @Author alex_shen
 * @Date 2024/3/6 - 23:51
 */
@Service
public class DefaultChainFactory {

    // 原型模式获取对象
    private final ApplicationContext applicationContext;
    // 存放策略链，策略ID -> 责任链
    private final Map<Long, ILogicChain> strategyChainGroup;
    // 仓储信息
    protected IStrategyRepository repository;

    public DefaultChainFactory(ApplicationContext applicationContext, IStrategyRepository repository) {
        this.applicationContext = applicationContext;
        this.repository = repository;
        this.strategyChainGroup = new ConcurrentHashMap<>();
    }

    public ILogicChain openLogicChain(Long strategyId) {
        ILogicChain cacheLogicChain = strategyChainGroup.get(strategyId);
        if (null != cacheLogicChain)
            return cacheLogicChain;

        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategy.ruleModels();

        // 如果未配置策略规则，则只装填一个默认责任链
        if (null == ruleModels || 0 == ruleModels.length) {
            ILogicChain ruleDefaultLogicChain = applicationContext.getBean(LogicModel.RULE_DEFAULT.getCode(), ILogicChain.class);
            // 写入缓存
            strategyChainGroup.put(strategyId, ruleDefaultLogicChain);
            return ruleDefaultLogicChain;
        }

        // 按照配置顺序装填用户配置的责任链；rule_blacklist、rule_weight 「注意此数据从Redis缓存中获取，如果更新库表，记得在测试阶段手动处理缓存」
        ILogicChain logicChain = applicationContext.getBean(ruleModels[0], ILogicChain.class);
        ILogicChain current = logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            ILogicChain nextChain = applicationContext.getBean(ruleModels[i], ILogicChain.class);
            current = current.appendNext(nextChain);
        }

        // 责任链的最后装填默认责任链
        current.appendNext(applicationContext.getBean(LogicModel.RULE_DEFAULT.getCode(), ILogicChain.class));
        // 写入缓存
        strategyChainGroup.put(strategyId, logicChain);

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
