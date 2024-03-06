package top.codingshen.domain.strategy.repository;

import org.springframework.stereotype.Repository;
import top.codingshen.domain.strategy.model.entity.StrategyAwardEntity;
import top.codingshen.domain.strategy.model.entity.StrategyEntity;
import top.codingshen.domain.strategy.model.entity.StrategyRuleEntity;
import top.codingshen.domain.strategy.model.valobj.StrategyAwardRuleModelVO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IStrategyRepository
 * @Description 策略仓储接口
 * @Author alex_shen
 * @Date 2024/3/4 - 15:09
 */
public interface IStrategyRepository {
    /**
     * 查询策略奖品列表
     *
     * @param strategyId 策略 id
     * @return 策略奖品列表
     */
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    /**
     * 存储策略奖品查询概率表
     *
     * @param key                                  策略 id
     * @param rateRange                            概率范围
     * @param shuffleStrategyAwardSearchRateTables 策略奖品查询概率表
     */
    void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTables);

    /**
     * 根据策略 id 获取概率范围
     *
     * @param strategyId 策略 id
     * @return 概率范围
     */
    int getRateRange(Long strategyId);

    int getRateRange(String key);

    /**
     * 根据 strategyId 策略, 使用 rateKey 进行抽奖
     *
     * @param strategyId 策略 id
     * @param rateKey    随机值
     * @return 奖品 id
     */
    Integer getStrategyAwardAssemble(Long strategyId, int rateKey);

    Integer getStrategyAwardAssemble(String key, int rateKey);

    /**
     * 根据策略 id 查询策略实体
     *
     * @param strategyId 策略 id
     * @return 策略实体
     */
    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    /**
     * 根据策略 id 和规则模型查询具体的策略规则
     *
     * @param strategyId 策略 id
     * @param ruleModel  规则模型
     * @return 策略规则
     */
    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);

    StrategyAwardRuleModelVO queryStrategyRuleModel(Long strategyId, Integer awardId);
}
