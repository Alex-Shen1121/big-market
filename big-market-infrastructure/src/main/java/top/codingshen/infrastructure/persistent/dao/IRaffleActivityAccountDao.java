package top.codingshen.infrastructure.persistent.dao;

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
}
