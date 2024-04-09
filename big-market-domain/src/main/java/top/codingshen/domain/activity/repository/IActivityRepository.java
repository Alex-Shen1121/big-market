package top.codingshen.domain.activity.repository;

import top.codingshen.domain.activity.model.aggregate.CreateOrderAggregate;
import top.codingshen.domain.activity.model.entity.ActivityCountEntity;
import top.codingshen.domain.activity.model.entity.ActivityEntity;
import top.codingshen.domain.activity.model.entity.ActivitySkuEntity;
import top.codingshen.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.util.Date;

/**
 * @ClassName IActivityRepository
 * @Description 活动仓储接口
 * @Author alex_shen
 * @Date 2024/3/31 - 18:00
 */

public interface IActivityRepository {

    ActivitySkuEntity queryActivitySku(Long sku);

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    void doSaveOrder(CreateOrderAggregate createOrderAggregate);

    void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);

    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);

    void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO);

    ActivitySkuStockKeyVO takeQueueValue();

    void clearQueueValue();

    void updateActivitySkuStock(Long sku);

    void clearActivitySkuStock(Long sku);
}
