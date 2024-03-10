package top.codingshen.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName StrategyAwardStockKeyVO
 * @Description 策略奖品库存 key 标示 值对象
 * @Author alex_shen
 * @Date 2024/3/9 - 17:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StrategyAwardStockKeyVO {
    // 策略 id
    private Long strategyId;

    // 奖品 id
    private Integer awardId;
}
