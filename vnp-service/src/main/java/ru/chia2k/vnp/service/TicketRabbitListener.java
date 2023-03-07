package ru.chia2k.vnp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class TicketRabbitListener {
    private final OrderService orderService;
    private final OrdersMailService mailService;

    @RabbitListener(queues = "#{amqpProperties.getParcelTicketResponseQueueName()}")
    public void TicketListener(Message message) {
        var parcelId = (Integer) message.getMessageProperties().getHeader("parcel_id");
        log.info("TicketListener - message received: parcelId = {}", parcelId.toString());


        var orderOpt = orderService.findByParcelId(parcelId);
        if (orderOpt.isEmpty()) {
            log.warn("No orders found for parcelId = {}", parcelId);
            return;
        }

        var order = orderOpt.get();
        mailService.sendTicket(order,
                message.getBody(),
                String.format("Этикетка %d.pdf", order.getParcel().id())
        );

        log.info("TicketListener - message processed: parcelId = {}", parcelId.toString());
    }
}
