package top.codingshen.domain.activity.service.rule;

/**
 * @ClassName IActionChainArmory
 * @Description 抽奖动作责任链装配
 * @Author alex_shen
 * @Date 2024/4/1 - 17:20
 */
public interface IActionChainArmory {

    IActionChain next();

    IActionChain appendNext(IActionChain next);
}
