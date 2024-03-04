package top.codingshen.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import top.codingshen.types.common.Constants;

/**
 * @ClassName StrategyEntity
 * @Description 策略实体
 * @Author alex_shen
 * @Date 2024/3/4 - 20:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyEntity {

    /**
     * 抽奖策略ID
     */
    private Long strategyId;
    /**
     * 抽奖策略描述
     */
    private String strategyDesc;
    /**
     * 抽奖规则模型 rule_weight,rule_blacklist
     */
    private String ruleModels;

    public String[] ruleModels() {
        if (StringUtils.isBlank(ruleModels)) return null;

        return ruleModels.split(Constants.SPLIT);
    }

    public String getRuleWeight() {
        String[] ruleModels = this.ruleModels();
        for (String ruleModel : ruleModels) {
            if ("rule_weight".equals(ruleModel))
                return ruleModel;
        }
        return null;
    }
}
