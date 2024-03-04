package top.codingshen.test.infrastructure;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.codingshen.infrastructure.persistent.dao.IStrategyRuleDao;
import top.codingshen.infrastructure.persistent.po.StrategyRulePO;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName AwardPODaoTest
 * @Description 策略规则持久化单元测试
 * @Author alex_shen
 * @Date 2024/3/3 - 21:09
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRuleDaoTest {

    @Resource
    private IStrategyRuleDao strategyRuleDao;

    @Test
    public void testQueryAwardList() {
        List<StrategyRulePO> strategyRules = strategyRuleDao.queryStrategyRuleList();
        log.info("测试结果: {}", JSON.toJSONString(strategyRules));
    }
}
