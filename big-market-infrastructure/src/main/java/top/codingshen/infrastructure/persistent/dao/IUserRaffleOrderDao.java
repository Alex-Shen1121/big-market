package top.codingshen.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.UserRaffleOrderPO;

/**
 * @description 用户抽奖订单表
 * @create 2024-04-03 15:57
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserRaffleOrderDao {
    void insert(UserRaffleOrderPO build);

    @DBRouter
    UserRaffleOrderPO queryNoUsedRaffleOrder(UserRaffleOrderPO userRaffleOrderReq);
}
