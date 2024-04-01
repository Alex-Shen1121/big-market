package top.codingshen.domain.activity.service;

import top.codingshen.domain.activity.model.entity.ActivityCountEntity;
import top.codingshen.domain.activity.model.entity.ActivityEntity;
import top.codingshen.domain.activity.model.entity.ActivitySkuEntity;
import top.codingshen.domain.activity.repository.IActivityRepository;
import top.codingshen.domain.activity.service.rule.factory.DefaultActivityChainFactory;

/**
 * @ClassName RaffleActivitySupport
 * @Description 抽奖活动支持类
 * @Author alex_shen
 * @Date 2024/4/1 - 17:10
 */
public class RaffleActivitySupport {
    protected IActivityRepository activityRepository;
    protected DefaultActivityChainFactory defaultActivityChainFactory;

    public RaffleActivitySupport(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
        this.activityRepository = activityRepository;
        this.defaultActivityChainFactory = defaultActivityChainFactory;
    }

    public ActivitySkuEntity queryActivitySku(Long sku) {
        return activityRepository.queryActivitySku(sku);
    }

    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        return activityRepository.queryRaffleActivityByActivityId(activityId);
    }

    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        return activityRepository.queryRaffleActivityCountByActivityCountId(activityCountId);
    }
}
