package top.codingshen.test.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.codingshen.domain.strategy.service.armory.IStrategyArmory;

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

    @Test
    public void test_assembleStrategyArmory() {
        boolean success = strategyArmory.assembleLotteryStrategy(100002L);
    }

    @Test
    public void test_getRandomAwardId() {
        for (int ii = 0; ii < 10000; ii++) {
            log.info("测试结果: {}", strategyArmory.getRandomAwardId(100002L));
        }
    }
}
