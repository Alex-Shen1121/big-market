package top.codingshen.domain.activity.service.rule;

/**
 * @ClassName AbstractActionChain
 * @Description 下单规则责任链抽象类
 * @Author alex_shen
 * @Date 2024/4/1 - 17:22
 */
public abstract class AbstractActionChain implements IActionChain{

    private IActionChain next;

    @Override
    public IActionChain next() {
        return next;
    }

    @Override
    public IActionChain appendNext(IActionChain next) {
        this.next = next;
        return next;
    }
}
