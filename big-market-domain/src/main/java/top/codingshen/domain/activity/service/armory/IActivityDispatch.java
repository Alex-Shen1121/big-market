package top.codingshen.domain.activity.service.armory;

import java.util.Date;

/**
 * @ClassName IActivityDispatch
 * @Description 活动调度[扣减库存]
 * @Author alex_shen
 * @Date 2024/4/8 - 21:55
 */
public interface IActivityDispatch {
    /**
     * 扣减活动商品库存
     *
     * @param sku         商品ID
     * @param endDateTime 活动结束时间
     * @return 是否扣减成功
     */
    boolean subtractionActivitySkuStock(Long sku, Date endDateTime);
}
