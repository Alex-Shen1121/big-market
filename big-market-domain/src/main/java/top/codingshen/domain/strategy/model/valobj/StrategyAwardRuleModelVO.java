package top.codingshen.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import top.codingshen.domain.strategy.service.rule.factory.DefaultLogicFactory;
import top.codingshen.types.common.Constants;

import java.util.ArrayList;
import java.util.List;

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

    public String[] raffleCenterRuleModelList() {
        List<String> ruleModelList = new ArrayList<>();
        String[] ruleModelValues = ruleModels.split(Constants.SPLIT);
        for (String ruleModelValue : ruleModelValues) {
            if (DefaultLogicFactory.LogicModel.isCenter(ruleModelValue)) {
                ruleModelList.add(ruleModelValue);
            }
        }
        return ruleModelList.toArray(new String[0]);
    }

}
