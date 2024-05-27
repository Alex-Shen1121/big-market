package top.codingshen.infrastructure.persistent.repository;

import org.springframework.stereotype.Repository;
import top.codingshen.domain.task.model.entity.TaskEntity;
import top.codingshen.domain.task.repository.ITaskRepository;
import top.codingshen.infrastructure.event.EventPublisher;
import top.codingshen.infrastructure.persistent.dao.ITaskDao;
import top.codingshen.infrastructure.persistent.po.TaskPO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 任务服务仓储实现
 * @create 2024-04-06 10:57
 */
@Repository
public class TaskRepository implements ITaskRepository {

    @Resource
    private ITaskDao taskDao;
    @Resource
    private EventPublisher eventPublisher;

    @Override
    public List<TaskEntity> queryNoSendMessageTaskList() {
        List<TaskPO> tasks = taskDao.queryNoSendMessageTaskList();

        List<TaskEntity> taskEntities = new ArrayList<>(tasks.size());
        for (TaskPO task : tasks) {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setUserId(task.getUserId());
            taskEntity.setTopic(task.getTopic());
            taskEntity.setMessageId(task.getMessageId());
            taskEntity.setMessage(task.getMessage());
            taskEntities.add(taskEntity);
        }
        return taskEntities;
    }

    @Override
    public void sendMessage(TaskEntity taskEntity) {
        eventPublisher.publish(taskEntity.getTopic(), taskEntity.getMessage());
    }

    @Override
    public void updateTaskSendMessageCompleted(String userId, String messageId) {
        TaskPO taskReq = new TaskPO();
        taskReq.setUserId(userId);
        taskReq.setMessageId(messageId);
        taskDao.updateTaskSendMessageCompleted(taskReq);
    }

    @Override
    public void updateTaskSendMessageFail(String userId, String messageId) {
        TaskPO taskReq = new TaskPO();
        taskReq.setUserId(userId);
        taskReq.setMessageId(messageId);
        taskDao.updateTaskSendMessageFail(taskReq);
    }

}
