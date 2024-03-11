package top.codingshen.domain.strategy.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import top.codingshen.domain.strategy.model.entity.RaffleAwardEntity;
import top.codingshen.domain.strategy.model.entity.RaffleFactorEntity;
import top.codingshen.domain.strategy.model.entity.StrategyAwardEntity;
import top.codingshen.domain.strategy.repository.IStrategyRepository;
import top.codingshen.domain.strategy.service.armory.IStrategyDispatch;
import top.codingshen.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import top.codingshen.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import top.codingshen.types.enums.ResponseCode;
import top.codingshen.types.exception.AppException;

/**
 * @ClassName AbstractRaffleStrategy
 * @Description 抽奖策略抽象类
 * @Author alex_shen
 * @Date 2024/3/5 - 14:06
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    // 策略仓储服务
    protected IStrategyRepository repository;

    // 策略调度服务: 只负责抽奖处理(通过新增接口的方式), 不需要使用方关心或者调用抽奖的初始化
    protected IStrategyDispatch strategyDispatch;

    // 责任链工厂: 从抽奖的规则中, 解耦出前置规则为责任链处理
    protected DefaultChainFactory defaultChainFactory;

    // 决策树工厂: 负责抽奖中到抽奖后的规则过滤, 如抽到 A 奖品 ID, 之后要做次数的判断和库存的扣减等.
    protected DefaultTreeFactory defaultTreeFactory;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
        this.defaultChainFactory = defaultChainFactory;
        this.defaultTreeFactory = defaultTreeFactory;
    }

    /**
     * 执行抽奖；用抽奖因子入参，执行抽奖计算，返回奖品信息
     *
     * @param raffleFactorEntity 抽奖因子实体对象，根据入参信息计算抽奖结果
     * @return 抽奖的奖品
     */
    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 1. 参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (strategyId == null || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 责任链抽奖计算 [初步的抽奖 id, 之后需要根据 id 处理抽奖]
        DefaultChainFactory.StrategyAwardVO chainStrategyAwardVO = raffleLogicChain(userId, strategyId);
        log.info("抽奖策略计算 - 责任链 userId:{}, strategyId:{}, awardId:{}, logicModel:{}", userId, strategyId, chainStrategyAwardVO.getAwardId(), chainStrategyAwardVO.getLogicModel());
        // 注: [黑名单, 权重] 等非默认抽奖的, 直接返回抽奖结果
        if (!DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode().equals(chainStrategyAwardVO.getLogicModel())) {
            // TODO awardConfig 暂时为空。黑名单指定积分奖品，后续需要在库表中配置上对应的1积分值，并获取到。
            return buildRaffleAwardEntity(strategyId, chainStrategyAwardVO.getAwardId(), null);
        }

        // 3. 规则树抽奖过滤 [奖品 id, 会根据抽奖次数判断, 库存判断, 兜底抽奖返回最终可获得的奖品信息]
        DefaultTreeFactory.StrategyAwardVO treeStrategyAwardVO = raffleLogicTree(userId, strategyId, chainStrategyAwardVO.getAwardId());
        log.info("抽奖策略计算 - 规则树 userId:{}, strategyId:{}, awardId:{}, awardRuleValue:{}", userId, strategyId, treeStrategyAwardVO.getAwardId(), treeStrategyAwardVO.getAwardRuleValue());

        // 4. 返回抽奖结果
        return buildRaffleAwardEntity(strategyId, treeStrategyAwardVO.getAwardId(), treeStrategyAwardVO.getAwardRuleValue());
    }


    private RaffleAwardEntity buildRaffleAwardEntity(Long strategyId, Integer awardId, String awardConfig) {
        StrategyAwardEntity strategyAward = repository.queryStrategyAwardEntity(strategyId, awardId);
        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .awardConfig(awardConfig)
                .sort(strategyAward.getSort())
                .build();
    }

    /**
     * 抽奖计算，责任链抽象方法
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    public abstract DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId);

    /**
     * 抽奖结果过滤，决策树抽象方法
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 过滤结果【奖品ID，会根据抽奖次数判断、库存判断、兜底兜里返回最终的可获得奖品信息】
     */
    public abstract DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId);
}
