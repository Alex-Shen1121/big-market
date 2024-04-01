package top.codingshen.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;
import top.codingshen.domain.activity.model.aggregate.CreateOrderAggregate;
import top.codingshen.domain.activity.model.entity.ActivityCountEntity;
import top.codingshen.domain.activity.model.entity.ActivityEntity;
import top.codingshen.domain.activity.model.entity.ActivityOrderEntity;
import top.codingshen.domain.activity.model.entity.ActivitySkuEntity;
import top.codingshen.domain.activity.model.valobj.ActivityStateVO;
import top.codingshen.domain.activity.repository.IActivityRepository;
import top.codingshen.infrastructure.persistent.dao.*;
import top.codingshen.infrastructure.persistent.po.*;
import top.codingshen.infrastructure.persistent.redis.IRedisService;
import top.codingshen.types.common.Constants;
import top.codingshen.types.enums.ResponseCode;
import top.codingshen.types.exception.AppException;

import javax.annotation.Resource;

/**
 * @ClassName ActivityRepository
 * @Description 活动仓储服务
 * @Author alex_shen
 * @Date 2024/3/31 - 18:01
 */
@Repository
@Slf4j
public class ActivityRepository implements IActivityRepository {
    @Resource
    private IRedisService redisService;
    @Resource
    private IRaffleActivityDao raffleActivityDao;
    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;
    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;
    @Resource
    private IRaffleActivityAccountDao raffleActivityAccountDao;
    @Resource
    private IRaffleActivityOrderDao raffleActivityOrderDao;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private IDBRouterStrategy dbRouter;

    @Override
    public ActivitySkuEntity queryActivitySku(Long sku) {
        RaffleActivitySkuPO raffleActivitySku = raffleActivitySkuDao.queryActivitySku(sku);
        return ActivitySkuEntity.builder().sku(raffleActivitySku.getSku()).activityId(raffleActivitySku.getActivityId()).activityCountId(raffleActivitySku.getActivityCountId()).stockCount(raffleActivitySku.getStockCount()).stockCountSurplus(raffleActivitySku.getStockCountSurplus()).build();
    }

    @Override
    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.ACTIVITY_KEY + activityId;
        ActivityEntity activityEntity = redisService.getValue(cacheKey);
        if (null != activityEntity) return activityEntity;
        // 从库中获取数据
        RaffleActivityPO raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(activityId);
        activityEntity = ActivityEntity.builder().activityId(raffleActivity.getActivityId()).activityName(raffleActivity.getActivityName()).activityDesc(raffleActivity.getActivityDesc()).beginDateTime(raffleActivity.getBeginDateTime()).endDateTime(raffleActivity.getEndDateTime()).strategyId(raffleActivity.getStrategyId()).state(ActivityStateVO.valueOf(raffleActivity.getState())).build();
        redisService.setValue(cacheKey, activityEntity);
        return activityEntity;
    }

    @Override
    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.ACTIVITY_COUNT_KEY + activityCountId;
        ActivityCountEntity activityCountEntity = redisService.getValue(cacheKey);
        if (null != activityCountEntity) return activityCountEntity;
        // 从库中获取数据
        RaffleActivityCountPO raffleActivityCount = raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(activityCountId);
        activityCountEntity = ActivityCountEntity.builder().activityCountId(raffleActivityCount.getActivityCountId()).totalCount(raffleActivityCount.getTotalCount()).dayCount(raffleActivityCount.getDayCount()).monthCount(raffleActivityCount.getMonthCount()).build();
        redisService.setValue(cacheKey, activityCountEntity);
        return activityCountEntity;
    }

    @Override
    public void doSaveOrder(CreateOrderAggregate createOrderAggregate) {
        // 订单对象
        ActivityOrderEntity activityOrderEntity = createOrderAggregate.getActivityOrderEntity();

        RaffleActivityOrderPO raffleActivityOrder = new RaffleActivityOrderPO();
        raffleActivityOrder.setSku(activityOrderEntity.getSku());
        raffleActivityOrder.setUserId(activityOrderEntity.getUserId());
        raffleActivityOrder.setActivityId(activityOrderEntity.getActivityId());
        raffleActivityOrder.setActivityName(activityOrderEntity.getActivityName());
        raffleActivityOrder.setStrategyId(activityOrderEntity.getStrategyId());
        raffleActivityOrder.setOrderId(activityOrderEntity.getOrderId());
        raffleActivityOrder.setOrderTime(activityOrderEntity.getOrderTime());
        raffleActivityOrder.setOutBusinessNo(activityOrderEntity.getOutBusinessNo());
        raffleActivityOrder.setTotalCount(activityOrderEntity.getTotalCount());
        raffleActivityOrder.setDayCount(activityOrderEntity.getDayCount());
        raffleActivityOrder.setMonthCount(activityOrderEntity.getMonthCount());
        raffleActivityOrder.setState(activityOrderEntity.getState().getCode());

        // 账户对象
        RaffleActivityAccountPO raffleActivityAccount = new RaffleActivityAccountPO();
        raffleActivityAccount.setUserId(createOrderAggregate.getUserId());
        raffleActivityAccount.setActivityId(createOrderAggregate.getActivityId());
        raffleActivityAccount.setTotalCount(createOrderAggregate.getTotalCount());
        raffleActivityAccount.setTotalCountSurplus(createOrderAggregate.getTotalCount());
        raffleActivityAccount.setDayCount(createOrderAggregate.getDayCount());
        raffleActivityAccount.setDayCountSurplus(createOrderAggregate.getDayCount());
        raffleActivityAccount.setMonthCount(createOrderAggregate.getMonthCount());
        raffleActivityAccount.setMonthCountSurplus(createOrderAggregate.getMonthCount());

        try {
            // 以用户 ID 作为切分键, 通过 doRouter 设定路由
            dbRouter.doRouter(createOrderAggregate.getUserId());
            // 编程式事务
            transactionTemplate.execute(status -> {
                try {
                    // 1. 写入订单
                    raffleActivityOrderDao.insert(raffleActivityOrder);
                    // 2. 更新账户
                    int count = raffleActivityAccountDao.updateAccountQuota(raffleActivityAccount);
                    // 3. 创建账户 - 更新为 0, 则账户不存在, 常见新账户
                    if (0 == count) {
                        raffleActivityAccountDao.insert(raffleActivityAccount);
                    }
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入订单记录, 唯一索引冲突, userId: {}, activityId: {}, sku: {}, bizId: {}", activityOrderEntity.getUserId(), activityOrderEntity.getActivityId(), activityOrderEntity.getSku(), activityOrderEntity.getOutBusinessNo());
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), ResponseCode.INDEX_DUP.getInfo());
                }
            });
        } finally {
            dbRouter.clear();
        }


    }
}
