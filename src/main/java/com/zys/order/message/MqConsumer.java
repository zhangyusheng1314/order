package com.zys.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
/**
 * rabbit的方式发送接收消息
 */
@Slf4j
@Component
public class MqConsumer {
    //手动创建队列
    //@RabbitListener(queues = "myQueue")
    //自动创建队列
    //@RabbitListener(queuesToDeclare =@Queue("myQueue"))
    //自动创建队列 Exchange和Queue的绑定
    @RabbitListener(bindings =@QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String message){
        log.info("MqConsumer: {}"+message);
    }

    @RabbitListener(bindings =@QueueBinding(
            value = @Queue("computerQueue"),
            key = "computer",
            exchange = @Exchange("orderExchange")
    ))
    public void processComputer(String message){
        log.info("computerConsumer: {}"+message);
    }

    @RabbitListener(bindings =@QueueBinding(
            value = @Queue("fruitQueue"),
            key = "fruit",
            exchange = @Exchange("orderExchange")
    ))
    public void processFruit(String message){
        log.info("FruitConsumer: {}"+message);
    }
}
