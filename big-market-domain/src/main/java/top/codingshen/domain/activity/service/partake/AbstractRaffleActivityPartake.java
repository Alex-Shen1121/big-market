package top.codingshen.domain.activity.service.partake;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import top.codingshen.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import top.codingshen.domain.activity.model.entity.ActivityEntity;
import top.codingshen.domain.activity.model.entity.PartakeRaffleActivityEntity;
import top.codingshen.domain.activity.model.entity.UserRaffleOrderEntity;
import top.codingshen.domain.activity.model.valobj.ActivityStateVO;
import top.codingshen.domain.activity.repository.IActivityRepository;
import top.codingshen.domain.activity.service.IRaffleActivityPartakeService;
import top.codingshen.types.enums.ResponseCode;
import top.codingshen.types.exception.AppException;

import java.util.Date;

/**
 * @ClassName AbstractRaffleActivityPartake
 * @Description 抽奖活动参与抽奖类
 * @Author alex_shen
 * @Date 2024/4/9 - 17:00
 */
@Slf4j
public abstract class AbstractRaffleActivityPartake implements IRaffleActivityPartakeService {

    protected final IActivityRepository activityRepository;

    public AbstractRaffleActivityPartake(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * 创建抽奖单: 用户参与抽奖活动, 扣减活动账户库存, 产生抽奖单, 如存在未被使用的抽奖单则直接返回已存在的抽奖单
     */
    @Override
    public UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity) {
        // 0. 基础信息
        String userId = partakeRaffleActivityEntity.getUserId();
        Long activityId = partakeRaffleActivityEntity.getActivityId();
        Date currentDate = new Date();

        // 1. 活动查询
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activityId);
        // 校验: 活动状态
        if (!ActivityStateVO.open.equals(activityEntity.getState())) {
            throw new AppException(ResponseCode.ACTIVITY_STATE_ERROR.getCode(), ResponseCode.ACTIVITY_STATE_ERROR.getInfo());
        }
        // 校验: 活动日期
        if(currentDate.before(activityEntity.getBeginDateTime()) || currentDate.after(activityEntity.getEndDateTime())){
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(), ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }

        // 2. 查询未被使用的活动参与订单记录
        UserRaffleOrderEntity userRaffleOrderEntity = activityRepository.queryNoUsedRaffleOrder(partakeRaffleActivityEntity);
        if(null != userRaffleOrderEntity) {
            log.info("创建参与活动订单[已存在未消费] userId:{} activityId:{}, userRaffleOrderEntity:{}", userId, activityId, JSON.toJSONString(userRaffleOrderEntity));
            return userRaffleOrderEntity;
        }

        // 3. 账户额度过滤 & 返回账户构建对象
        CreatePartakeOrderAggregate createPartakeOrderAggregate = this.doFilterAccount(userId, activityId, currentDate);

        // 4. 构建订单
        UserRaffleOrderEntity userRaffleOrder = this.buildUserRaffleOrder(userId, activityId, currentDate);

        // 5. 填充抽奖单实体对象
        createPartakeOrderAggregate.setUserRaffleOrderEntity(userRaffleOrder);

        // 6. 保存聚合对象 - 一个领域内的一个聚合是一个事务操作
        activityRepository.saveCreatePartakeOrderAggregate(createPartakeOrderAggregate);

        return userRaffleOrder;
    }

    protected abstract UserRaffleOrderEntity buildUserRaffleOrder(String userId, Long activityId, Date currentDate);

    protected abstract CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate);

}
