package top.codingshen.domain.strategy.service.rule.chain.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.codingshen.domain.strategy.repository.IStrategyRepository;
import top.codingshen.domain.strategy.service.rule.chain.AbstractLogicChain;
import top.codingshen.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import top.codingshen.types.common.Constants;

import javax.annotation.Resource;

/**
 * @ClassName BlackListLogicChain
 * @Description 黑名单责任链方法
 * @Author alex_shen
 * @Date 2024/3/6 - 23:10
 */
@Slf4j
@Component("rule_blacklist")
public class BlackListLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository repository;

    /**
     * 责任链接口
     *
     * @param userId     用户 id
     * @param strategyId 策略 id
     * @return 奖品 id
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("抽奖责任链 - 黑名单开始 userId:{}, strategyId:{}, ruleModel:{}", userId, strategyId, ruleModel());
        // 查询黑名单列表
        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());

        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        // 过滤其他规则
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            // 黑名单用户, 直接返回黑名单对应
            if (userId.equals(userBlackId)) {
                log.info("抽奖责任链 - 黑名单接管 userId:{}, ruleModel:{}, awardId:{}", userId, ruleModel(), awardId);
                return DefaultChainFactory.StrategyAwardVO.builder()
                        .awardId(awardId)
                        .logicModel(ruleModel())
                        .build();
            }
        }

        // 过滤其他责任链
        log.info("抽奖责任链 - 黑名单放行 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_BLACKLIST.getCode();
    }
}
