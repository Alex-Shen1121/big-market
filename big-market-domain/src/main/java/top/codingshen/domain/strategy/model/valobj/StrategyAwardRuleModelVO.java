package top.codingshen.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName StrategyAwardRuleModelVO
 * @Description 抽奖策略规值对象; 值对象, 没有唯一 ID, 仅限于从数据库查询对象
 * @Author alex_shen
 * @Date 2024/3/6 - 13:43
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StrategyAwardRuleModelVO {

    private String ruleModels;

}
