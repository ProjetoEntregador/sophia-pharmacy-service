package com.sophia.sophia_pharmacy_service.message;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.queue.incoming}")
    private String incomingQueue;

    @Value("${rabbitmq.queue.outgoing}")
    private String outgoingQueue;

    @Value("${rabbitmq.routing.incoming}")
    private String incomingRouting;

    @Value("${rabbitmq.routing.outgoing}")
    private String outgoingRouting;

    @Bean
    public TopicExchange pharmacyExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue incomingQueue() {
        return QueueBuilder.durable(incomingQueue).build();
    }

    @Bean
    public Queue outgoingQueue() {
        return QueueBuilder.durable(outgoingQueue).build();
    }

    @Bean
    public Binding incomingBinding() {
        return BindingBuilder
                .bind(incomingQueue())
                .to(pharmacyExchange())
                .with(incomingRouting);
    }

    @Bean
    public Binding outgoingBinding() {
        return BindingBuilder
                .bind(outgoingQueue())
                .to(pharmacyExchange())
                .with(outgoingRouting);
    }

    @Bean
    public MessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template =
                new RabbitTemplate(connectionFactory);

        template.setMessageConverter(jacksonConverter());

        return template;
    }
}