package top.codingshen.domain.award.service;

import top.codingshen.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @description 奖品服务接口
 * @create 2024-04-06 09:03
 */
public interface IAwardService {

    void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);

}
