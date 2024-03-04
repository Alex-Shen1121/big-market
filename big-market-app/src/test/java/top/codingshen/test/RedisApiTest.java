package top.codingshen.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RMap;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.codingshen.infrastructure.persistent.redis.IRedisService;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApiTest {

    @Resource
    private IRedisService redisService;

    @Test
    public void test() {
        RMap<Object, Object> map = redisService.getMap("strategy_id_100002");
        map.put(10, 1010);
        map.put(2, 102);
        map.put(3, 103);
        map.put(4, 104);
        map.put(5, 105);
        map.put(6, 106);
        map.put(7, 107);
        map.put(8, 108);
        map.put(9, 109);
        map.put("sss", "sdasd");
        log.info("测试结果: {}", redisService.getFromMap("strategy_id_100002", "sss").toString());
    }

}
