package top.codingshen.infrastructure.event;

import com.alibaba.fastjson2.JSON;
import com.esotericsoftware.minlog.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.codingshen.types.event.BaseEvent;

/**
 * @ClassName EventPublisher
 * @Description 消息发布
 * @Author alex_shen
 * @Date 2024/4/8 - 22:14
 */
@Slf4j
@Component
public class EventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public  void publish(String topic, BaseEvent.EventMessage<?> eventMessage) {
        try {
            String messageJson = JSON.toJSONString(eventMessage);
            rabbitTemplate.convertAndSend(topic, messageJson);
            log.info("发送 MQ 消息, topic:{}, message:{}", topic, messageJson);
        }catch (Exception e){
            log.error("发送 MQ 消息失败, topic:{}, message:{}", topic, JSON.toJSONString(eventMessage), e);
            throw e;
        }
    }
}
