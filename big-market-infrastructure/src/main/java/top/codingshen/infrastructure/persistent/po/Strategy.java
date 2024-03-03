package top.codingshen.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName Strategy
 * @Description strategy 抽奖策略表
 * @Author alex_shen
 * @Date 2024/3/2 - 21:59
 */
@Data
public class Strategy {
    /**
     * 自增ID
     **/
    private Long id;
    /**
     * 抽奖策略ID
     **/
    private Long strategyId;
    /**
     * 抽奖策略描述
     **/
    private String strategyDesc;
    /**
     * 创建时间
     **/
    private Date createTime;
    /**
     * 更新时间
     **/
    private Date updateTime;
}
