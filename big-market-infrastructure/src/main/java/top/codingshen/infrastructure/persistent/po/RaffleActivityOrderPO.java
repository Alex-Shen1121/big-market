package top.codingshen.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @description 抽奖活动单 持久化对象
 * @create 2024-03-02 13:21
 */
@Data
public class RaffleActivityOrderPO {

    /**
     * 自增ID
     */
    private Long id;
    /**
     * sku
     */
    private Long sku;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 抽奖策略ID
     */
    private Long strategyId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 业务仿重ID - 外部透传的，确保幂等
     */
    private String outBusinessNo;
    /**
     * 总次数
     */
    private Integer totalCount;

    /**
     * 日次数
     */
    private Integer dayCount;

    /**
     * 月次数
     */
    private Integer monthCount;


    /**
     * 订单状态
     */
    private String state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
