package top.codingshen.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName StrategyAwardEntity
 * @Description 策略奖品实体
 * @Author alex_shen
 * @Date 2024/3/4 - 15:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardEntity {
    /**
     * 抽奖策略ID
     **/
    private Long strategyId;
    /**
     * 抽奖奖品ID - 内部流转使用
     **/
    private Integer awardId;
    /**
     * 奖品库存总量
     **/
    private Integer awardCount;
    /**
     * 奖品库存剩余
     **/
    private Integer awardCountSurplus;
    /**
     * 奖品中奖概率
     **/
    private BigDecimal awardRate;

}