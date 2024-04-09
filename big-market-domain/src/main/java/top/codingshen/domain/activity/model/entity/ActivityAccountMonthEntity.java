package top.codingshen.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description 抽奖活动账户表-月次数实体对象
 * @create 2024-04-03 15:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityAccountMonthEntity {

    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;
    /** 月（yyyy-mm） */
    private String month;
    /** 月次数 */
    private Integer monthCount;
    /** 月次数-剩余 */
    private Integer monthCountSurplus;

}
