package ru.chia2k.vnp.config;

import javafx.beans.binding.MapExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;

@Slf4j
@EnableRabbit
@RequiredArgsConstructor
@Configuration
public class RabbitConfig {
    private final AmqpProperties properties;
//    @Bean
//    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }

    @Bean
    public DirectExchange parcelExchange() {
        return new DirectExchange(properties.getParcelExchangeName());
    }

    @Bean
    public Queue responseParcelTicketQueue() {
        return new Queue(properties.getParcelTicketResponseQueueName(), true);
    }

    @Bean
    public Binding responseQueueBinding() {
        return BindingBuilder.bind(responseParcelTicketQueue())
                .to(parcelExchange())
                .with(properties.getParcelTicketResponseRoutingKey());
    }



}
