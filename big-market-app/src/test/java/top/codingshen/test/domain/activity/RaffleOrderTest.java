package top.codingshen.test.domain.activity;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.codingshen.domain.activity.model.entity.ActivityOrderEntity;
import top.codingshen.domain.activity.model.entity.ActivityShopCartEntity;
import top.codingshen.domain.activity.model.entity.SkuRechargeEntity;
import top.codingshen.domain.activity.service.IRaffleOrder;

import javax.annotation.Resource;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 抽奖活动订单单测
 * @create 2024-03-16 11:51
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleOrderTest {

    @Resource
    private IRaffleOrder raffleOrder;

    @Test
    public void test_createRaffleActivityOrder() {
        ActivityShopCartEntity activityShopCartEntity = new ActivityShopCartEntity();
        activityShopCartEntity.setUserId("xiaofuge");
        activityShopCartEntity.setSku(9011L);
        ActivityOrderEntity raffleActivityOrder = raffleOrder.createRaffleActivityOrder(activityShopCartEntity);
        log.info("测试结果：{}", JSON.toJSONString(raffleActivityOrder));
    }

    @Test
    public void test_createSkuRechargeOrder() {
        SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
        skuRechargeEntity.setUserId("xiaofuge");
        skuRechargeEntity.setSku(9011L);
        // outBusinessNo 作为幂等仿重使用，同一个业务单号2次使用会抛出索引冲突 Duplicate entry '700091009111' for key 'uq_out_business_no' 确保唯一性。
        skuRechargeEntity.setOutBusinessNo("700091009114");
        String orderId = raffleOrder.createSkuRechargeOrder(skuRechargeEntity);
        log.info("测试结果：{}", orderId);
    }

}
