package top.codingshen.test.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.codingshen.infrastructure.persistent.dao.IRaffleActivitySkuDao;
import top.codingshen.infrastructure.persistent.po.RaffleActivitySkuPO;

import javax.annotation.Resource;

/**
 * @ClassName RaffleActivitySkuDaoTest
 * @Description description
 * @Author alex_shen
 * @Date 2024/3/31 - 17:03
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivitySkuDaoTest {

    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;

    @Test
    public void test_queryActivitySku() {
        RaffleActivitySkuPO raffleActivitySkuPO = raffleActivitySkuDao.queryActivitySku(9011L);
        log.info("测试结果: {}", raffleActivitySkuPO);
    }
}
