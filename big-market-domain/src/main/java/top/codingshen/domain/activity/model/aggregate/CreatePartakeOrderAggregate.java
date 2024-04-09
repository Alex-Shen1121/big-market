package top.codingshen.domain.activity.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.codingshen.domain.activity.model.entity.ActivityAccountDayEntity;
import top.codingshen.domain.activity.model.entity.ActivityAccountEntity;
import top.codingshen.domain.activity.model.entity.ActivityAccountMonthEntity;
import top.codingshen.domain.activity.model.entity.UserRaffleOrderEntity;

/**
 * @ClassName CreatePartakeOrderAggregate
 * @Description 参与活动订单聚合对象
 * @Author alex_shen
 * @Date 2024/4/9 - 17:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePartakeOrderAggregate {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 账户总额度
     */
    private ActivityAccountEntity activityAccountEntity;

    /**
     * 是否存在月账户
     */
    private boolean isExistAccountMonth = true;

    /**
     * 账户月额度
     */
    private ActivityAccountMonthEntity activityAccountMonthEntity;

    /**
     * 是否存在日账户
     */
    private boolean isExistAccountDay = true;

    /**
     * 账户日额度
     */
    private ActivityAccountDayEntity activityAccountDayEntity;

    /**
     * 抽奖单实体
     */
    private UserRaffleOrderEntity userRaffleOrderEntity;

}
