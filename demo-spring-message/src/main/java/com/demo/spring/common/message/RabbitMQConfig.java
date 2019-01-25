package com.demo.spring.common.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

@Slf4j
public class RabbitMQConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    protected ConnectionFactory connectionFactory(String host, int port, String username,
            String password, String virtualHost) {
        log.info("创建RabbitMQ连接工厂...");
        log.info("host:{}, port:{}, username:{}, password:{}, virtualHost:{}",
                host, port, username, password, virtualHost);
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    protected RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        log.info("创建rabbitAdmin...");
        return new RabbitAdmin(connectionFactory);
    }

    protected RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        log.info("创建rabbitTemplate...");
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReturnCallback(this::returnedMessage);
        rabbitTemplate.setConfirmCallback(this::confirm);
        return rabbitTemplate;
    }

    protected SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        log.info("创建rabbitListenerContainerFactory...");
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(connectionFactory);
        listenerContainerFactory.setMessageConverter(new SimpleMessageConverter());
        return listenerContainerFactory;
    }

    protected RabbitAdmin registryRoutes(RabbitAdmin rabbitAdmin, Queue queue, Exchange exchange, String... routingKeys){
        for (String routingKey : routingKeys) {
            Binding productToCustomerRequest = BindingBuilder.bind(queue).to(exchange)
                    .with(routingKey).noargs();
            rabbitAdmin.declareBinding(productToCustomerRequest);
        }
        return rabbitAdmin;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText,
            String exchange, String routingKey) {
        log.info("message:{}, replyCode:{}, replyText:{}, exchange:{}, routingKey:{}",
                message, replyCode, replyText, exchange, routingKey);
    }
}
