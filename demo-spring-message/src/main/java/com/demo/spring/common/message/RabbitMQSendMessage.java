package com.demo.spring.common.message;

import com.demo.spring.common.utils.JsonMapper;
import com.demo.spring.common.utils.XStreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQSendMessage {


    enum ConvertType {
        JSON,
        XML;
    }

    public <T> void sendMessage(RabbitTemplate rabbitTemplate, String exchange, String routingKey, T sendMessage, ConvertType convertType){
        String message;
        switch (convertType) {
            case XML:
                message = XStreamUtil.toXml(sendMessage);
                break;
            case JSON:
            default:
                message = JsonMapper.toJsonString(sendMessage);
                break;
        }
        log.info("exchange:{}, routingKey:{}, message:{}", exchange, routingKey, message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

}
