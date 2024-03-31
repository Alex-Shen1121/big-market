package top.codingshen.domain.activity.service;

import org.springframework.stereotype.Service;
import top.codingshen.domain.activity.repository.IActivityRepository;

/**
 * @ClassName RaffleActivityService
 * @Description 抽奖活动服务
 * @Author alex_shen
 * @Date 2024/3/31 - 22:05
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity{

    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }

}
