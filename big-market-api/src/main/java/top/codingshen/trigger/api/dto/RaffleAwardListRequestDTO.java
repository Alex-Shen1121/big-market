package top.codingshen.trigger.api.dto;

import lombok.Data;

/**
 * @ClassName RaffleAwardListRequestDTO
 * @Description 抽奖奖品列表, 请求对象
 * @Author alex_shen
 * @Date 2024/3/10 - 20:46
 */
@Data
public class RaffleAwardListRequestDTO {
    // 抽奖策略 ID
    private Long strategyId;
}
