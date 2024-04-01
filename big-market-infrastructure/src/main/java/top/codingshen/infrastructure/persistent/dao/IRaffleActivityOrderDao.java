package top.codingshen.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.RaffleActivityOrderPO;

import java.util.List;

/**
 * @description 抽奖活动单Dao
 * @create 2024-03-09 10:08
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IRaffleActivityOrderDao {

    void insert(RaffleActivityOrderPO raffleActivityOrder);

    @DBRouter
    List<RaffleActivityOrderPO> queryRaffleActivityOrderByUserId(String userId);

}
