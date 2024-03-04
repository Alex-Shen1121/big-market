package top.codingshen.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.StrategyPO;

import java.util.List;

/**
 * @ClassName IStrategyDao
 * @Description 抽奖策略表 Dao
 * @Author alex_shen
 * @Date 2024/3/2 - 22:23
 */
@Mapper
public interface IStrategyDao {
    List<StrategyPO> queryStrategyList();

    StrategyPO queryStrategyByStrategyId(Long strategyId);
}
