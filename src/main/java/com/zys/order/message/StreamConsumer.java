package com.zys.order.message;

import com.zys.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * stream的方式发送接收消息
 */
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamConsumer {
    //消息的回调
    @StreamListener(value = StreamClient.INPUT)
    @SendTo(StreamClient.OUTPUT)
    public String processInput(OrderDTO message){
        log.info("StreamReceiver:" + message);
        //消费完通知生产者
        return "receive .";
    }
    //消息的发送
    @StreamListener(value = StreamClient.OUTPUT)
    public void processOutput(String message){
        log.info("StreamOutputReceiver:" + message);
    }
}
