package top.codingshen.domain.activity.service;

import top.codingshen.domain.activity.model.entity.PartakeRaffleActivityEntity;
import top.codingshen.domain.activity.model.entity.UserRaffleOrderEntity;

/**
 * @ClassName IRaffleActivityPartakeService
 * @Description 抽奖活动参与服务
 * @Author alex_shen
 * @Date 2024/4/9 - 16:33
 */
public interface IRaffleActivityPartakeService {

    /**
     * 创建抽奖单: 用户参与抽奖活动, 扣减活动账户库存, 产生抽奖单, 如存在未被使用的抽奖单则直接返回已存在的抽奖单
     */
    UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);
}
