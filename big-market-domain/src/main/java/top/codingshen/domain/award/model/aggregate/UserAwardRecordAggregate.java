package top.codingshen.domain.award.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.codingshen.domain.award.model.entity.TaskEntity;
import top.codingshen.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @description 用户中奖记录聚合对象 【聚合代表一个事务操作】
 * @create 2024-04-06 09:40
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardRecordAggregate {

    private UserAwardRecordEntity userAwardRecordEntity;

    private TaskEntity taskEntity;

}
