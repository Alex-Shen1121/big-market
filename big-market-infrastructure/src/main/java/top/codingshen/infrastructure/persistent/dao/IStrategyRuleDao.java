package top.codingshen.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.StrategyRule;

import java.util.List;

/**
 * @ClassName IStrategyRuleDao
 * @Description 策略规则配置 Dao
 * @Author alex_shen
 * @Date 2024/3/2 - 22:24
 */
@Mapper
public interface IStrategyRuleDao {

    List<StrategyRule> queryStrategyRuleList();

}
