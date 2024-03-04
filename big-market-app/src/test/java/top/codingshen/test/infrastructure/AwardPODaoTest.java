package top.codingshen.test.infrastructure;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.codingshen.infrastructure.persistent.dao.IAwardDao;
import top.codingshen.infrastructure.persistent.po.AwardPO;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName AwardPODaoTest
 * @Description 奖品持久化单元测试
 * @Author alex_shen
 * @Date 2024/3/3 - 21:09
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardPODaoTest {

    @Resource
    private IAwardDao awardDao;

    @Test
    public void test_queryAwardList() {
        List<AwardPO> awardPOS = awardDao.queryAwardList();
        log.info("测试结果: {}", JSON.toJSONString(awardPOS));
    }
}
