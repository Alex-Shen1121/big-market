package top.codingshen.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RaffleAwardListRequestDTO
 * @Description 抽奖奖品列表, 响应对象
 * @Author alex_shen
 * @Date 2024/3/10 - 20:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaffleAwardListResponseDTO {
    // 奖品 id
    private Integer awardId;

    // 奖品标题
    private String awardTitle;

    // 奖品副标题[eg:抽奖 1 次后解锁]
    private String awardSubtitle;

    // 排序编号
    private Integer sort;
}
