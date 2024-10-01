package com.namelessCodeF.codeforces.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    public static final String RESULT_QUEUE_NAME = "resultQueue";
    public static final String QUEUE_NAME = "submissionQueue";
    public static final String EXCHANGE_NAME = "submissionExchange";
    public static final String ROUTING_KEY = "submissionRoutingKey";


    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue submissionQueue() {
        return new Queue(QUEUE_NAME, true); // Durable queue
    }



    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    // Define the Submission Exchange
    @Bean
    public DirectExchange submissionExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }



    // Bind the Submission Queue to the Submission Exchange with a Routing Key
    @Bean
    public Binding submissionBinding(Queue submissionQueue, DirectExchange submissionExchange) {
        return BindingBuilder.bind(submissionQueue).to(submissionExchange).with(ROUTING_KEY);
    }



}
