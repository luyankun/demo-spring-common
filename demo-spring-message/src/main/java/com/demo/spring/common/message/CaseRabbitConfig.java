package com.demo.spring.common.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Slf4j
@EnableRabbit
@Configuration
public class CaseRabbitConfig extends RabbitMQConfig {

    @Value(value = "${rabbit.case.exchange}")
    private String caseTopicExchange;
    @Value(value = "${rabbit.case.queue}")
    private String caseQueue;
    @Value(value = "${rabbit.case.pattern.routing.one}")
    private String caseReqOne;
    @Value(value = "${rabbit.case.pattern.routing.two}")
    private String caseReqTwo;


    @Bean(value = "caseConnectionFactory")
    @Override
    public ConnectionFactory connectionFactory(
            @Value(value = "${rabbit.case.host}") String host,
            @Value(value = "${rabbit.case.port}") int port,
            @Value(value = "${rabbit.case.username}") String username,
            @Value(value = "${rabbit.case.password}") String password,
            @Value(value = "${rabbit.case.virtualHost}") String virtualHost) {
        return super.connectionFactory(host, port, username, password, virtualHost);
    }


    @Bean(value = "caseRabbitAdmin")
    @Override
    public RabbitAdmin rabbitAdmin(
            @Qualifier(value = "caseConnectionFactory") ConnectionFactory connectionFactory) {
        return super.rabbitAdmin(connectionFactory);
    }

    @Bean(value = "caseRabbitTemplate")
    @Override
    public RabbitTemplate rabbitTemplate(
            @Qualifier(value = "caseConnectionFactory") ConnectionFactory connectionFactory) {
        return super.rabbitTemplate(connectionFactory);
    }

    @Lazy
    @Bean(value = "caseRabbitListenerContainerFactory")
    @Override
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            @Qualifier(value = "caseConnectionFactory") ConnectionFactory connectionFactory) {
        return super.rabbitListenerContainerFactory(connectionFactory);
    }

    /**
     * 创建exchange
     * @return
     */
    @Bean(value = "caseTopicExchange")
    public TopicExchange caseTopicExchange(){
        return new TopicExchange(caseTopicExchange);
    }

    /**
     * 创建queue
     * @return
     */
    @Bean(value = "caseQueue")
    public Queue caseQueue() {
        return new Queue(caseQueue);
    }

    @Bean(value = "caseRegistryRoutes")
    protected RabbitAdmin registryRoutes(
            @Qualifier(value = "caseRabbitAdmin") RabbitAdmin rabbitAdmin,
            @Qualifier(value = "caseQueue") Queue queue,
            @Qualifier(value = "caseTopicExchange") Exchange exchange) {
        String[] routingKeys = new String[] {
                caseReqOne,
                caseReqTwo
        };
        return super.registryRoutes(rabbitAdmin, queue, exchange, routingKeys);
    }
}
