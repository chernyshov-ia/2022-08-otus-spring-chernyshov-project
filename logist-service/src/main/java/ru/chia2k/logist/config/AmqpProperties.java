package ru.chia2k.logist.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AmqpProperties {
    private String parcelExchangeName = "parcel";
    private String parcelTicketRequestRoutingKey = "parcel.request.ticket";
    @Value("${spring.rabbitmq.parcel.ticket-request-queue-name:parcel.request.ticket}")
    private String parcelTicketRequestQueueName;
}
