package top.codingshen.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;

/**
 * @ClassName RaffleFactorEntity
 * @Description 抽奖因子实体
 * @Author alex_shen
 * @Date 2024/3/5 - 14:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaffleFactorEntity {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 策略ID
     */
    private Long strategyId;
}
