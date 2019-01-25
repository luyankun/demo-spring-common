package com.demo.spring.common.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomSendMessage {

    @Autowired
    private RabbitMQSendMessage rabbitMQSendMessage;

    @Autowired
    @Qualifier(value = "customRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${rabbit.custom.exchange}")
    private String exchange;
    @Value(value = "${rabbit.custom.pattern.routing.one}")
    private String routingOne;
    @Value(value = "${rabbit.custom.pattern.routing.two}")
    private String routingTwo;

    public <T> void sendOneMessage(T sendMessage){
        rabbitMQSendMessage
                .sendMessage(rabbitTemplate, exchange, routingOne, sendMessage, RabbitMQSendMessage.ConvertType.JSON);
    }
    public <T> void sendTwoMessage(T sendMessage){
        rabbitMQSendMessage
                .sendMessage(rabbitTemplate, exchange, routingOne, sendMessage, RabbitMQSendMessage.ConvertType.JSON);
    }
}
