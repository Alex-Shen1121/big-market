package top.codingshen.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.RaffleActivityAccountMonthPO;

/**
 * @description 抽奖活动账户表-月次数
 * @create 2024-04-03 15:57
 */
@Mapper
public interface IRaffleActivityAccountMonthDao {

    @DBRouter
    RaffleActivityAccountMonthPO queryActivityAccountMonthByUserId(RaffleActivityAccountMonthPO raffleActivityAccountMonthReq);

    int updateActivityAccountMonthSubtractionQuota(RaffleActivityAccountMonthPO raffleActivityAccountMonth);

    void insertActivityAccountMonth(RaffleActivityAccountMonthPO raffleActivityAccountMonth);

}
