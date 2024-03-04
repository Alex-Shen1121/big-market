package top.codingshen.test.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.codingshen.domain.strategy.service.armory.IStrategyArmory;
import top.codingshen.domain.strategy.service.armory.IStrategyDispatch;

import javax.annotation.Resource;

/**
 * @ClassName StrategyArmoryTest
 * @Description description
 * @Author alex_shen
 * @Date 2024/3/4 - 16:43
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyArmoryTest {
    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private IStrategyDispatch strategyDispatch;

    @Before
    public void test_assembleStrategyArmory() {
        boolean success = strategyArmory.assembleLotteryStrategy(100001L);
        log.info("测试结果: {}", success);
    }

    @Test
    public void test_getRandomAwardId() {
        for (int ii = 0; ii < 10000; ii++) {
            log.info("测试结果: {} - 奖品 id 值", strategyDispatch.getRandomAwardId(100001L));
        }
    }

    @Test
    public void test_getRandomAwardId_ruleWeightValue() {
        for (int i = 0; i < 10000; i++) {
            log.info("测试结果: {} - 4000 策略配置", strategyDispatch.getRandomAwardId(100001L, "4000:102,103,104,105"));
            //log.info("测试结果: {} - 5000 策略配置", strategyDispatch.getRandomAwardId(100001L, "5000:102,103,104,105,106,107"));
            //log.info("测试结果: {} - 6000 策略配置", strategyDispatch.getRandomAwardId(100001L, "6000:102,103,104,105,106,107,108,109"));
        }

    }
}
