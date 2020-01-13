package com.zys.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


public interface StreamClient {
    String INPUT = "message";

    String OUTPUT="message2";

    @Input(StreamClient.INPUT)
    SubscribableChannel input();
    @Output(StreamClient.OUTPUT)
    MessageChannel output();
}
