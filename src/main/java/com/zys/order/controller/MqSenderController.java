package com.zys.order.controller;

import com.zys.order.dto.OrderDTO;
import com.zys.order.message.StreamClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.stream.Stream;

@RestController
@RequestMapping("/mq")
public class MqSenderController {
    private @Autowired
    AmqpTemplate amqpTemplate;
    private @Autowired
    StreamClient streamClient;

    @RequestMapping("/producer")
    public void send(){
        amqpTemplate.convertAndSend("myQueue","now:"+new Date());
    }
    //绑定orderExchange 发送到computer队列
    @RequestMapping("/computerProducer")
    public void send2Computer(){
        amqpTemplate.convertAndSend("orderExchange","computer","now:"+new Date());
    }
    //绑定orderExchange 发送到fruit队列
    @RequestMapping("/fruitProducer")
    public void send2Fruit(){
        amqpTemplate.convertAndSend("orderExchange","fruit","now:"+new Date());
    }
    //Stream的方式发送消息
    @RequestMapping("/streamProducer")
    public void sendMessageByStream(){
        OrderDTO order = new OrderDTO();
        order.setOrderId("11232132");
        streamClient.output().send(MessageBuilder.withPayload(order).build());
    }
}
