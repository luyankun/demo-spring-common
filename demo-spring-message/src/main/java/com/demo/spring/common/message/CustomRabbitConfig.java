package com.demo.spring.common.message;

import com.demo.spring.common.utils.ThreadPool;
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
import org.springframework.context.annotation.*;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@EnableRabbit
@Configuration
public class CustomRabbitConfig extends RabbitMQConfig {

    @Value(value = "${rabbit.custom.exchange}")
    private String customTopicExchange;
    @Value(value = "${rabbit.custom.queue}")
    private String customQueue;
    @Value(value = "${rabbit.custom.pattern.routing.one}")
    private String customReqOne;
    @Value(value = "${rabbit.custom.pattern.routing.two}")
    private String customReqTwo;


    @Bean(value = "customConnectionFactory")
    @Primary
    @Override
    public ConnectionFactory connectionFactory(
            @Value(value = "${rabbit.custom.host}") String host,
            @Value(value = "${rabbit.custom.port}") int port,
            @Value(value = "${rabbit.custom.username}") String username,
            @Value(value = "${rabbit.custom.password}") String password,
            @Value(value = "${rabbit.custom.virtualHost}") String virtualHost) {
        return super.connectionFactory(host, port, username, password, virtualHost);
    }


    @Bean(value = "customRabbitAdmin")
    @Override
    public RabbitAdmin rabbitAdmin(
            @Qualifier(value = "customConnectionFactory") ConnectionFactory connectionFactory) {
        return super.rabbitAdmin(connectionFactory);
    }

    @Bean(value = "customRabbitTemplate")
    @Override
    public RabbitTemplate rabbitTemplate(
            @Qualifier(value = "customConnectionFactory") ConnectionFactory connectionFactory) {
        return super.rabbitTemplate(connectionFactory);
    }

    @Bean(value = "threadPoolExecutor")
    public Executor threadPoolExecutor() {
        log.info("创建线程池...");
        ThreadPoolExecutor executor = ThreadPool.init().getThreadPoolExecutor();
        System.out.println(executor.getActiveCount());
        return executor;
    }

//    @Lazy
    @Bean(value = "customRabbitListenerContainerFactory")
    @Override
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            @Qualifier(value = "customConnectionFactory") ConnectionFactory connectionFactory,
            @Qualifier(value = "threadPoolExecutor") Executor threadPoolExecutor) {
        return super.rabbitListenerContainerFactory(connectionFactory, threadPoolExecutor);
    }

    /**
     * 创建exchange
     * @return
     */
    @Bean(value = "customTopicExchange")
    public TopicExchange customTopicExchange(){
        return new TopicExchange(customTopicExchange);
    }

    /**
     * 创建queue
     * @return
     */
    @Bean(value = "customQueue")
    public Queue customQueue() {
        return new Queue(customQueue);
    }

    @Bean(value = "customRegistryRoutes")
    protected RabbitAdmin registryRoutes(
            @Qualifier(value = "customRabbitAdmin") RabbitAdmin rabbitAdmin,
            @Qualifier(value = "customQueue") Queue queue,
            @Qualifier(value = "customTopicExchange") Exchange exchange) {
        String[] routingKeys = new String[] {
                customReqOne,
                customReqTwo
        };
        return super.registryRoutes(rabbitAdmin, queue, exchange, routingKeys);
    }
}
