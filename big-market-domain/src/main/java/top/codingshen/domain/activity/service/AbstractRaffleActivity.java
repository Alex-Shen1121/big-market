package top.codingshen.domain.activity.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import top.codingshen.domain.activity.model.entity.*;
import top.codingshen.domain.activity.repository.IActivityRepository;

/**
 * @ClassName AbstractRaffleActivity
 * @Description 抽奖活动抽象类 定义标准的流程
 * @Author alex_shen
 * @Date 2024/3/31 - 17:57
 */
@Slf4j
public abstract class AbstractRaffleActivity implements IRaffleOrder{

    protected IActivityRepository activityRepository;

    public AbstractRaffleActivity(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
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
}
