package top.codingshen.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.TaskPO;

import java.util.List;

/**
 * @description 任务表，发送MQ
 * @create 2024-04-03 15:57
 */
@Mapper
public interface ITaskDao {

    void insert(TaskPO task);

    @DBRouter
    void updateTaskSendMessageCompleted(TaskPO task);

    @DBRouter
    void updateTaskSendMessageFail(TaskPO task);

    List<TaskPO> queryNoSendMessageTaskList();

}
