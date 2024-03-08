package top.codingshen.test.infrastructure;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.codingshen.domain.strategy.model.valobj.RuleTreeVO;
import top.codingshen.domain.strategy.repository.IStrategyRepository;

import javax.annotation.Resource;

/**
 * @ClassName StrategyRepositoryTest
 * @Description 仓储数据查询
 * @Author alex_shen
 * @Date 2024/3/8 - 14:43
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class StrategyRepositoryTest {

    @Resource
    private IStrategyRepository strategyRepository;

    @Test
    public void queryRuleTreeVOByTreeId() {
        RuleTreeVO ruleTreeVO = strategyRepository.queryRuleTreeVOByTreeId("tree_lock");
        log.info("测试结果: {}", JSON.toJSONString(ruleTreeVO));
    }

}
