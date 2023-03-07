package ru.chia2k.vnp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("amqpProperties")
@Getter
public class AmqpProperties {
    private String parcelExchangeName = "parcel";
    private String parcelTicketRequestRoutingKey = "parcel.request.ticket";
    @Value("parcel.response.ticket:${spring.application.name:vnp-service}")
    private String parcelTicketResponseQueueName = "parcel.response.ticket:vnp-service";
    @Value("parcel.response.ticket:${spring.application.name:vnp-service}")
    private String parcelTicketResponseRoutingKey;
}
