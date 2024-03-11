package top.codingshen.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RaffleRequestDTO
 * @Description 抽奖请求参数
 * @Author alex_shen
 * @Date 2024/3/10 - 21:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaffleRequestDTO {
    // 抽奖策略 id
    private Long strategyId;
}
