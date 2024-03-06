package top.codingshen.domain.strategy.model.entity;

import lombok.*;
import top.codingshen.domain.strategy.model.valobj.RuleLogicCheckTypeVO;

/**
 * @ClassName RuleActionEntity
 * @Description 规则动作实体
 * @Author alex_shen
 * @Date 2024/3/5 - 14:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleActionEntity <T extends RuleActionEntity.RaffleEntity> {

    private String code = RuleLogicCheckTypeVO.ALLOW.getCode();
    private String info = RuleLogicCheckTypeVO.ALLOW.getInfo();
    private String ruleModel;
    private T data;

    // 抽奖实体基类
    static public class RaffleEntity {

    }

    /**
     * 抽奖前置规则实体
     * eg: 黑名单 rule_blacklist
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static public class RaffleBeforeEntity extends RaffleEntity {
        /**
         * 策略 id
         */
        private Long strategyId;

        /**
         * 权重值 key: 用于抽奖时可以选择权重抽奖
         */
        private String ruleWeightValueKey;

        /**
         * 奖品 id
         */
        private Integer awardId;
    }

    /**
     * 抽奖中规则实体
     * eg: 黑名单 rule_blacklist
     */
    static public class RaffleCenterEntity extends RaffleEntity {

    }

    /**
     * 抽奖后规则实体
     * eg: 黑名单 rule_blacklist
     */
    static public class RaffleAfterEntity extends RaffleEntity {

    }
}
