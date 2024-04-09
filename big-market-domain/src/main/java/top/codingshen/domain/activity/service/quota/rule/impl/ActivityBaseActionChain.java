package top.codingshen.domain.activity.service.quota.rule.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.codingshen.domain.activity.model.entity.ActivityCountEntity;
import top.codingshen.domain.activity.model.entity.ActivityEntity;
import top.codingshen.domain.activity.model.entity.ActivitySkuEntity;
import top.codingshen.domain.activity.model.valobj.ActivityStateVO;
import top.codingshen.domain.activity.service.quota.rule.AbstractActionChain;
import top.codingshen.types.enums.ResponseCode;
import top.codingshen.types.exception.AppException;

import java.util.Date;

/**
 * @description 活动规则过滤【日期、状态】
 * @create 2024-03-23 10:23
 */
@Slf4j
@Component("activity_base_action")
public class ActivityBaseActionChain extends AbstractActionChain {

    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {

        log.info("活动责任链-基础信息【有效期、状态】校验开始。sku：{}, activityId：{}", activitySkuEntity.getSku(), activityEntity.getActivityId());

        // 1. 校验: 活动状态
        if(!ActivityStateVO.open.equals(activityEntity.getState())){
            throw new AppException(ResponseCode.ACTIVITY_STATE_ERROR.getCode(), ResponseCode.ACTIVITY_STATE_ERROR.getInfo());
        }

        // 2. 校验: 活动日期 [活动开始时间 <= 当前时间 <= 活动结束时间]
        Date currentDate = new Date();
        if(currentDate.before(activityEntity.getBeginDateTime()) || currentDate.after(activityEntity.getEndDateTime())){
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(), ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }

        // 3. 校验: 活动库存
        if(activitySkuEntity.getStockCountSurplus() <= 0){
            throw new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(), ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());
        }

        return next().action(activitySkuEntity, activityEntity, activityCountEntity);
    }

}
