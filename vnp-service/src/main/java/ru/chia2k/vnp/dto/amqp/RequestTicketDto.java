package ru.chia2k.vnp.dto.amqp;

import lombok.*;

@RequiredArgsConstructor
@Getter
public class RequestTicketDto {
    private final Integer parcelId;
}
