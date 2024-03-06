package top.codingshen.domain.strategy.service.rule.chain;

/**
 * @ClassName AbstractLogicChain
 * @Description description
 * @Author alex_shen
 * @Date 2024/3/6 - 22:59
 */
public abstract class AbstractLogicChain implements ILogicChain{
    private ILogicChain next;

    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next = next;
        return next;
    }

    @Override
    public ILogicChain next() {
        return next;
    }

    protected abstract String ruleModel();
}
