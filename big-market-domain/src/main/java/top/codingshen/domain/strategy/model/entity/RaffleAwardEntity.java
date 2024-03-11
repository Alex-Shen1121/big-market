package top.codingshen.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RaffleAwardEntity
 * @Description 抽奖奖品实体
 * @Author alex_shen
 * @Date 2024/3/5 - 14:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaffleAwardEntity {
    /**
     * 奖品ID
     */
    private Integer awardId;
    /**
     * 奖品配置信息
     */
    private String awardConfig;
    /**
     * 奖品顺序号
     */
    private Integer sort;
}
