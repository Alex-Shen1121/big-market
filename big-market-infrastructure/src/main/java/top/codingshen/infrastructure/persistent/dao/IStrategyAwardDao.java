package top.codingshen.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.StrategyAwardPO;

import java.util.List;

/**
 * @ClassName IStrategyAwardDao
 * @Description 抽奖策略奖品明细配置 Dao
 * @Author alex_shen
 * @Date 2024/3/2 - 22:23
 */
@Mapper
public interface IStrategyAwardDao {
    List<StrategyAwardPO> queryStrategyAwardList();

    List<StrategyAwardPO> queryStrategyAwardListByStrategyId(Long strategyId);
}
