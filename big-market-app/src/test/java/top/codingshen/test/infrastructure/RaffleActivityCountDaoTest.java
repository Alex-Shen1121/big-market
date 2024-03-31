package top.codingshen.test.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.codingshen.infrastructure.persistent.dao.IRaffleActivityCountDao;
import top.codingshen.infrastructure.persistent.po.RaffleActivityCountPO;

import javax.annotation.Resource;

/**
 * @ClassName RaffleActivityCountDaoTest
 * @Description description
 * @Author alex_shen
 * @Date 2024/3/31 - 16:58
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityCountDaoTest {

    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;
    @Test
    public void test_queryRaffleActivityCountByActivityCountId() {
        RaffleActivityCountPO raffleActivityCountPO = raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(11101L);
        System.out.println(raffleActivityCountPO);
    }
}
