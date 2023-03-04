package ru.chia2k.vnp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.chia2k.vnp.config.AmqpProperties;
import ru.chia2k.vnp.dto.amqp.RequestTicketDto;

@RequiredArgsConstructor
@Service
@Slf4j
public class RabbitTicketService implements TicketService {
    private final RabbitTemplate rabbitTemplate;
    private final AmqpProperties properties;
    private final ObjectMapper mapper;

    @Override
    public void sendRequestTicket(Integer parcelId) {
        log.debug("Отправляем запрос по AMQP на генерацию этикетки( parcelId = {} )", parcelId);

        String request;
        try {
            request = mapper.writeValueAsString(new RequestTicketDto(parcelId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        rabbitTemplate.convertAndSend(
                properties.getParcelExchangeName(),
                properties.getParcelTicketRequestRoutingKey(),
                request,
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setReplyTo(properties.getParcelTicketResponseRoutingKey());
                        message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
                        return message;
                    }
                }
        );
        log.debug("Отправлен запрос по AMQP на генерацию этикетки( parcelId = {} )", parcelId);
    }
}
