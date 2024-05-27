package top.codingshen.domain.award.repository;

import top.codingshen.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @description 奖品仓储服务
 * @create 2024-04-06 09:02
 */
public interface IAwardRepository {

    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);

}
