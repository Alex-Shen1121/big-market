package top.codingshen.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.RaffleActivityAccountDayPO;

/**
 * @description 抽奖活动账户表-日次数
 * @create 2024-04-03 15:56
 */
@Mapper
public interface IRaffleActivityAccountDayDao {

    @DBRouter
    RaffleActivityAccountDayPO queryActivityAccountDayByUserId(RaffleActivityAccountDayPO raffleActivityAccountDayReq);

    int updateActivityAccountDaySubtractionQuota(RaffleActivityAccountDayPO raffleActivityAccountDay);

    void insertActivityAccountDay(RaffleActivityAccountDayPO raffleActivityAccountDay);
}
