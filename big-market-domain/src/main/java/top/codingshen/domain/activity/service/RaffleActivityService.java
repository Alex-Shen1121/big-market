package top.codingshen.domain.activity.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import top.codingshen.domain.activity.model.aggregate.CreateOrderAggregate;
import top.codingshen.domain.activity.model.entity.*;
import top.codingshen.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import top.codingshen.domain.activity.model.valobj.OrderStateVO;
import top.codingshen.domain.activity.repository.IActivityRepository;
import top.codingshen.domain.activity.service.rule.factory.DefaultActivityChainFactory;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ClassName RaffleActivityService
 * @Description 抽奖活动服务
 * @Author alex_shen
 * @Date 2024/3/31 - 22:05
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity implements ISkuStock {

    public RaffleActivityService(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
        super(activityRepository, defaultActivityChainFactory);
    }

    @Override
    protected void doSaveOrder(CreateOrderAggregate createOrderAggregate) {
        activityRepository.doSaveOrder(createOrderAggregate);
    }

    @Override
    protected CreateOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        // 订单实体对象
        ActivityOrderEntity activityOrderEntity = new ActivityOrderEntity();
        activityOrderEntity.setUserId(skuRechargeEntity.getUserId());
        activityOrderEntity.setSku(skuRechargeEntity.getSku());
        activityOrderEntity.setActivityId(activityEntity.getActivityId());
        activityOrderEntity.setActivityName(activityEntity.getActivityName());
        activityOrderEntity.setStrategyId(activityEntity.getStrategyId());

        // 公司里一般会有专门的雪花算法UUID服务，我们这里直接生成个12位就可以了。
        activityOrderEntity.setOrderId(RandomStringUtils.randomNumeric(12));
        activityOrderEntity.setOrderTime(new Date());
        activityOrderEntity.setTotalCount(activityCountEntity.getTotalCount());
        activityOrderEntity.setDayCount(activityCountEntity.getDayCount());
        activityOrderEntity.setMonthCount(activityCountEntity.getMonthCount());
        activityOrderEntity.setState(OrderStateVO.completed);
        activityOrderEntity.setOutBusinessNo(skuRechargeEntity.getOutBusinessNo());

        // 构建聚合对象
        return CreateOrderAggregate.builder()
                .userId(skuRechargeEntity.getUserId())
                .activityId(activitySkuEntity.getActivityId())
                .totalCount(activityCountEntity.getTotalCount())
                .dayCount(activityCountEntity.getDayCount())
                .monthCount(activityCountEntity.getMonthCount())
                .activityOrderEntity(activityOrderEntity)
                .build();
    }

    /**
     * 获取活动sku库存消耗队列
     *
     * @return 奖品库存Key信息
     * @throws InterruptedException 异常
     */
    @Override
    public ActivitySkuStockKeyVO takeQueueValue() throws InterruptedException {
        return activityRepository.takeQueueValue();

    }

    /**
     * 清空队列
     */
    @Override
    public void clearQueueValue() {
        activityRepository.clearQueueValue();
    }

    /**
     * 延迟队列 + 任务趋势更新活动sku库存
     *
     * @param sku 活动商品
     */
    @Override
    public void updateActivitySkuStock(Long sku) {
        activityRepository.updateActivitySkuStock(sku);
    }

    /**
     * 缓存库存以消耗完毕，清空数据库库存
     *
     * @param sku 活动商品
     */
    @Override
    public void clearActivitySkuStock(Long sku) {
        activityRepository.clearActivitySkuStock(sku);
    }
}
