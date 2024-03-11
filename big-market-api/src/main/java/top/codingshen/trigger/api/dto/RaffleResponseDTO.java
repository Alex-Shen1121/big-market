package top.codingshen.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RaffleRequestDTO
 * @Description 抽奖响应参数
 * @Author alex_shen
 * @Date 2024/3/10 - 21:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaffleResponseDTO {
    // 奖品 ID
    private Integer awardId;
    // 排序编号[策略奖品配置的奖品顺序编号]
    private Integer awardIndex;
}
