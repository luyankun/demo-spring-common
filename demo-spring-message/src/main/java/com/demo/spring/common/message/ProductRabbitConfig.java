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
public class ProductRabbitConfig extends RabbitMQConfig {

    @Value(value = "${rabbit.product.exchange}")
    private String productTopicExchange;
    @Value(value = "${rabbit.product.queue}")
    private String productQueue;
    @Value(value = "${rabbit.product.pattern.routing.one}")
    private String productReqOne;
    @Value(value = "${rabbit.product.pattern.routing.two}")
    private String productReqTwo;


    @Bean(value = "productConnectionFactory")
    @Override
    public ConnectionFactory connectionFactory(
            @Value(value = "${rabbit.product.host}") String host,
            @Value(value = "${rabbit.product.port}") int port,
            @Value(value = "${rabbit.product.username}") String username,
            @Value(value = "${rabbit.product.password}") String password,
            @Value(value = "${rabbit.product.virtualHost}") String virtualHost) {
        return super.connectionFactory(host, port, username, password, virtualHost);
    }


    @Bean(value = "productRabbitAdmin")
    @Override
    public RabbitAdmin rabbitAdmin(
            @Qualifier(value = "productConnectionFactory") ConnectionFactory connectionFactory) {
        return super.rabbitAdmin(connectionFactory);
    }

    @Bean(value = "productRabbitTemplate")
    @Override
    public RabbitTemplate rabbitTemplate(
            @Qualifier(value = "productConnectionFactory") ConnectionFactory connectionFactory) {
        return super.rabbitTemplate(connectionFactory);
    }

    @Lazy
    @Bean(value = "productRabbitListenerContainerFactory")
    @Override
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            @Qualifier(value = "productConnectionFactory") ConnectionFactory connectionFactory) {
        return super.rabbitListenerContainerFactory(connectionFactory);
    }

    /**
     * 创建exchange
     * @return
     */
    @Bean(value = "productTopicExchange")
    public TopicExchange productTopicExchange(){
        return new TopicExchange(productTopicExchange);
    }

    /**
     * 创建queue
     * @return
     */
    @Bean(value = "productQueue")
    public Queue productQueue() {
        return new Queue(productQueue);
    }

    @Bean(value = "productRegistryRoutes")
    protected RabbitAdmin registryRoutes(
            @Qualifier(value = "productRabbitAdmin") RabbitAdmin rabbitAdmin,
            @Qualifier(value = "productQueue") Queue queue,
            @Qualifier(value = "productTopicExchange") Exchange exchange) {
        String[] routingKeys = new String[] {
                productReqOne,
                productReqTwo
        };
        return super.registryRoutes(rabbitAdmin, queue, exchange, routingKeys);
    }
}
