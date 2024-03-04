package top.codingshen.test.infrastructure;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.codingshen.infrastructure.persistent.dao.IStrategyDao;
import top.codingshen.infrastructure.persistent.po.StrategyPO;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName AwardPODaoTest
 * @Description 抽奖策略持久化单元测试
 * @Author alex_shen
 * @Date 2024/3/3 - 21:09
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyDaoTest {

    @Resource
    private IStrategyDao strategyDao;

    @Test
    public void testQueryAwardList() {
        List<StrategyPO> strategies = strategyDao.queryStrategyList();
        log.info("测试结果: {}", JSON.toJSONString(strategies));
    }
}
