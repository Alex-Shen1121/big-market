package top.codingshen.domain.activity.service;

import top.codingshen.domain.activity.model.entity.ActivityOrderEntity;
import top.codingshen.domain.activity.model.entity.ActivityShopCartEntity;

/**
 * @ClassName IRaffleOrder
 * @Description 抽奖活动订单接口
 * @Author alex_shen
 * @Date 2024/3/31 - 17:36
 */
public interface IRaffleOrder {

    /**
     * 以 sku 创建抽奖活动订单, 获取参与抽奖资格(可消耗次数)
     *
     * @param activityShopCartEntity 活动 sku 实体, 通过 sku 领取活动
     * @return 活动参与记录实体
     */
    ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity);
}
