package com.demo.spring.common.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: 鲁砚琨
 * @Date: 2019/4/29 10:42
 * @Version: v1.0
 */
@Component
public class CustomMessage {


    @RabbitListener(queues = "${rabbit.custom.queue}")
    public void onMessage(Message message){
        System.out.println("消费者: " + new String(message.getBody()));
    }
}
