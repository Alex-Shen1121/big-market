package top.codingshen.domain.activity.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import top.codingshen.domain.activity.model.aggregate.CreateOrderAggregate;
import top.codingshen.domain.activity.model.entity.*;
import top.codingshen.domain.activity.repository.IActivityRepository;
import top.codingshen.domain.activity.service.rule.IActionChain;
import top.codingshen.domain.activity.service.rule.factory.DefaultActivityChainFactory;
import top.codingshen.types.enums.ResponseCode;
import top.codingshen.types.exception.AppException;

/**
 * @ClassName AbstractRaffleActivity
 * @Description 抽奖活动抽象类 定义标准的流程
 * @Author alex_shen
 * @Date 2024/3/31 - 17:57
 */
@Slf4j
public abstract class AbstractRaffleActivity extends RaffleActivitySupport implements IRaffleOrder {
    public AbstractRaffleActivity(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
        super(activityRepository, defaultActivityChainFactory);
    }

    /**
     * 以 sku 创建抽奖活动订单, 获取参与抽奖资格(可消耗次数)
     *
     * @param activityShopCartEntity 活动 sku 实体, 通过 sku 领取活动
     * @return 活动参与记录实体
     */
    @Override
    public ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity) {
        // 1. 通过 sku 查询活动信息
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(activityShopCartEntity.getSku());
        // 2. 查询活动信息
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 3. 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        log.info("查询结果：{} {} {}", JSON.toJSONString(activitySkuEntity), JSON.toJSONString(activityEntity), JSON.toJSONString(activityCountEntity));

        return ActivityOrderEntity.builder().build();
    }

    @Override
    public String createSkuRechargeOrder(SkuRechargeEntity skuRechargeEntity) {
        // 1. 参数校验
        String userId = skuRechargeEntity.getUserId();
        Long sku = skuRechargeEntity.getSku();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
        if (null == sku || StringUtils.isBlank(userId) || StringUtils.isBlank(outBusinessNo)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 查询基础信息
        // 2.1 通过sku查询活动信息
        ActivitySkuEntity activitySkuEntity = queryActivitySku(sku);
        // 2.2 查询活动信息
        ActivityEntity activityEntity = queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 2.3 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        // 3. 活动动作规则校验
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        boolean success = actionChain.action(activitySkuEntity, activityEntity, activityCountEntity);

        // 4. 构建订单聚合对象
        CreateOrderAggregate createOrderAggregate = buildOrderAggregate(skuRechargeEntity, activitySkuEntity, activityEntity, activityCountEntity);

        // 5. 保存订单
        doSaveOrder(createOrderAggregate);

        // 6. 返回单号
        return createOrderAggregate.getActivityOrderEntity().getOrderId();
    }

    protected abstract void doSaveOrder(CreateOrderAggregate createOrderAggregate);

    protected abstract CreateOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);

}
