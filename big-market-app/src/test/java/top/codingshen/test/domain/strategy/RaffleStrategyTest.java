package top.codingshen.test.domain.strategy;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import top.codingshen.domain.strategy.model.entity.RaffleAwardEntity;
import top.codingshen.domain.strategy.model.entity.RaffleFactorEntity;
import top.codingshen.domain.strategy.service.IRaffleStock;
import top.codingshen.domain.strategy.service.IRaffleStrategy;
import top.codingshen.domain.strategy.service.armory.IStrategyArmory;
import top.codingshen.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import top.codingshen.domain.strategy.service.rule.tree.impl.RuleLockLogicTreeNode;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @description 抽奖策略测试
 * @create 2024-01-06 13:28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleStrategyTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private IRaffleStrategy raffleStrategy;

    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;

    @Resource
    private RuleLockLogicTreeNode ruleLockLogicTreeNode;

    @Resource
    private IRaffleStock raffleStock;

    @Before
    public void setUp() {
        //log.info("strategy {} 装配 测试结果: {}", 100001L, strategyArmory.assembleLotteryStrategy(100001L));
        //log.info("strategy {} 装配 测试结果: {}", 100002L, strategyArmory.assembleLotteryStrategy(100002L));
        //log.info("strategy {} 装配 测试结果: {}", 100003L, strategyArmory.assembleLotteryStrategy(100003L));
        log.info("strategy {} 装配 测试结果: {}", 100003L, strategyArmory.assembleLotteryStrategy(100006L));

        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 400L);
        ReflectionTestUtils.setField(ruleLockLogicTreeNode, "userRaffleCount", 10L);
    }

    @Test
    public void test_performRaffle() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {

            RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                    .userId("scy")
                    .strategyId(100006L)
                    .build();

            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

            log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
            log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
        }

        new CountDownLatch(1).await();

    }

    @Test
    public void test_performRaffle_blacklist() {
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("user003")  // 黑名单用户 user001,user002,user003
                .strategyId(100001L)
                .build();

        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
    }

    /**
     * 次数错校验，抽奖n次后解锁。100003 策略，你可以通过调整 @Before 的 setUp 方法中个人抽奖次数来验证。比如最开始设置0，之后设置10 ReflectionTestUtils.setField(ruleLockLogicFilter, "userRaffleCount", 10L);
     */
    @Test
    public void test_raffle_center_rule_lock() {
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("scy")
                .strategyId(100003L)
                .build();

        for (int i = 0; i < 20; i++) {
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
            log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
            log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
        }
    }

}
