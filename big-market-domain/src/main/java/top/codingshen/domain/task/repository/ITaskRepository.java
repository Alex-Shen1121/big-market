package top.codingshen.domain.task.repository;

import top.codingshen.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @description 任务服务仓储接口
 * @create 2024-04-06 10:51
 */
public interface ITaskRepository {

    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageCompleted(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);

}
