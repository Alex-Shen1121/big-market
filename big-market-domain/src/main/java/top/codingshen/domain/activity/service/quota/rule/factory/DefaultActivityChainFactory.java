package top.codingshen.domain.activity.service.quota.rule.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import top.codingshen.domain.activity.service.quota.rule.IActionChain;

import java.util.Map;

/**
 * @ClassName DefaultActivityChainFactory
 * @Description 责任链工厂
 * @Author alex_shen
 * @Date 2024/4/1 - 17:29
 */
@Service
public class DefaultActivityChainFactory {
    private final IActionChain actionChain;

    public DefaultActivityChainFactory(Map<String, IActionChain> actionChainGroup) {
        actionChain = actionChainGroup.get(ActionModel.activity_base_action.code);
        actionChain.appendNext(actionChainGroup.get(ActionModel.activity_sku_stock_action.code));
    }

    public IActionChain openActionChain() {
        return actionChain;
    }

    @Getter
    @AllArgsConstructor
    public enum ActionModel {
        activity_base_action("activity_base_action", "活动的库存、时间校验"),
        activity_sku_stock_action("activity_sku_stock_action", "活动sku库存"),
        ;

        private final String code;
        private final String info;
    }
}
