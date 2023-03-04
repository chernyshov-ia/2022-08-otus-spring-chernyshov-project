package ru.chia2k.logist.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@RequiredArgsConstructor
@Configuration
public class RabbitConfig {
    private final AmqpProperties properties;

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange parcelExchange() {
        return new DirectExchange(properties.getParcelExchangeName());
    }

    @Bean
    public Queue requestParcelTicketQueue() {
        return new Queue(properties.getParcelTicketRequestQueueName(), true);
    }

    @Bean
    public Binding requestTicketQueueBinding() {
        return BindingBuilder.bind(requestParcelTicketQueue())
                .to(parcelExchange())
                .with(properties.getParcelTicketRequestRoutingKey());
    }
}
