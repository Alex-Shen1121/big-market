package top.codingshen.domain.activity.service.rule;

import top.codingshen.domain.activity.model.entity.ActivityCountEntity;
import top.codingshen.domain.activity.model.entity.ActivityEntity;
import top.codingshen.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @ClassName IActionChain
 * @Description 下单规则过滤接口
 * @Author alex_shen
 * @Date 2024/4/1 - 17:19
 */
public interface IActionChain extends IActionChainArmory{

    boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);

}
