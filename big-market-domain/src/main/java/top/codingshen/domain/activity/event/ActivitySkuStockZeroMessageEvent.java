package top.codingshen.domain.activity.event;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.codingshen.types.event.BaseEvent;

import java.util.Date;

/**
 * @ClassName ActivitySkuStockZeroMessageEvent
 * @Description description
 * @Author alex_shen
 * @Date 2024/4/8 - 22:21
 */
@Component
public class ActivitySkuStockZeroMessageEvent extends BaseEvent<Long> {

    @Value("${spring.rabbitmq.topic.activity_sku_stock_zero}")
    private String topic;

    @Override
    public EventMessage<Long> buildEventMessage(Long sku) {
        return EventMessage.<Long>builder()
                .id(RandomStringUtils.randomNumeric(11))
                .timestamp(new Date())
                .data(sku)
                .build();
    }

    @Override
    public String topic() {
        return topic;
    }
}
