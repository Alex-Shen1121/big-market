package top.codingshen.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SkuRechargeEntity
 * @Description 活动商品充值实体对象
 * @Author alex_shen
 * @Date 2024/4/1 - 16:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuRechargeEntity {
    /** 用户ID */
    private String userId;
    /** 商品SKU - activity + activity count */
    private Long sku;
    /** 幂等业务单号, 外部谁充值透传, 来保证幂等(多次调用也能确保结果唯一, 不会多次充值) */
    private String outBusinessNo;
}
