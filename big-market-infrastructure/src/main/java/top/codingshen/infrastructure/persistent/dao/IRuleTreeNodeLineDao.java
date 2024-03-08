package top.codingshen.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.RuleTreeNodeLinePO;

import java.util.List;

/**
 * @description 规则树节点连线表DAO
 * @create 2024-02-03 08:44
 */
@Mapper
public interface IRuleTreeNodeLineDao {

    List<RuleTreeNodeLinePO> queryRuleTreeNodeLineListByTreeId(String treeId);

}
