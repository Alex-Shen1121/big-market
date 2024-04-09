package top.codingshen.domain.activity.service.armory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.codingshen.domain.activity.model.entity.ActivitySkuEntity;
import top.codingshen.domain.activity.repository.IActivityRepository;
import top.codingshen.types.common.Constants;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ClassName ActivityArmory
 * @Description 活动装备预热
 * @Author alex_shen
 * @Date 2024/4/8 - 17:43
 */
@Slf4j
@Service
public class ActivityArmory implements IActivityArmory, IActivityDispatch {

    @Resource
    private IActivityRepository activityRepository;

    @Override
    public boolean assembleActivitySku(Long sku) {
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(sku);
        cacheActivitySkuStockCount(sku, activitySkuEntity.getStockCount());

        return false;
    }

    private void cacheActivitySkuStockCount(Long sku, Integer stockCount) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        activityRepository.cacheActivitySkuStockCount(cacheKey, stockCount);
    }

    @Override
    public boolean subtractionActivitySkuStock(Long sku, Date endDateTime) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        return activityRepository.subtractionActivitySkuStock(sku, cacheKey, endDateTime);
    }
}
