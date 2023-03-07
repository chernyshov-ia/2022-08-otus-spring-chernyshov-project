package ru.chia2k.logist.tickets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.chia2k.logist.config.AmqpProperties;
import ru.chia2k.logist.tickets.dto.RequestTicketDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitRequestTicketListener {
    private final ObjectMapper mapper;
    private final TicketManager ticketManager;
    private final AmqpProperties properties;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "${spring.rabbitmq.parcel.ticket-request-queue-name:parcel.request.ticket}",
            id = "ticket.request.listener")
    public void receiveRequestTicket(RequestTicketDto request, Message message) {
        var routingKey = message.getMessageProperties().getReplyTo();
        var exchange = properties.getParcelExchangeName();

        log.info("receiveRequestTicket - request received: parcelId = {}, response routing key = {}",
                request.getParcelId(), message.getMessageProperties().getReplyTo());

        try {
            var payload = ticketManager.getTicket(request.getParcelId(),"pdf");
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setCorrelationId(message.getMessageProperties().getCorrelationId());
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_BYTES);
            messageProperties.setHeader("parcel_id", request.getParcelId());

            var responseMessage = MessageBuilder.withBody(payload).andProperties(messageProperties).build();
            rabbitTemplate.send(exchange, routingKey, responseMessage);

            log.info("receiveRequestTicket - response sent: {}", responseMessage.toString());
        } catch (Exception e) {
            log.error("{}",e);
        }
    }
}
