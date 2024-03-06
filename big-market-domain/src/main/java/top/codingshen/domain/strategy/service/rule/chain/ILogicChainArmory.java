package top.codingshen.domain.strategy.service.rule.chain;

/**
 * @ClassName ILogicChainArmony
 * @Description 责任链装配
 * @Author alex_shen
 * @Date 2024/3/7 - 03:16
 */
public interface ILogicChainArmory{
    ILogicChain appendNext(ILogicChain next);

    ILogicChain next();
}
