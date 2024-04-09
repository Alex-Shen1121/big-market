package top.codingshen.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.RaffleActivityAccountPO;

/**
 * @description 抽奖活动账户表
 * @create 2024-03-09 10:05
 */
@Mapper
public interface IRaffleActivityAccountDao {
    int updateAccountQuota(RaffleActivityAccountPO raffleActivityAccount);

    void insert(RaffleActivityAccountPO raffleActivityAccount);

    @DBRouter
    RaffleActivityAccountPO queryActivityAccountByUserId(RaffleActivityAccountPO raffleActivityAccountReq);

    int updateActivityAccountSubtractionQuota(RaffleActivityAccountPO raffleActivityAccount);

    void updateActivityAccountMonthSurplusImageQuota(RaffleActivityAccountPO raffleActivityAccount);

    void updateActivityAccountDaySurplusImageQuota(RaffleActivityAccountPO raffleActivityAccount);
}
