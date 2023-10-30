package com.fakeperson.worker.configuration;

import com.fakeperson.worker.Worker;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Bean
    public CachingConnectionFactory connectionFactory() {
        var factory = new CachingConnectionFactory("localhost", 9191);
        factory.setUsername("user");
        factory.setPassword("password");
        return factory;

    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue rabbitQueue() {
        return new Queue("fakePersonQueue", false);
    }
}
