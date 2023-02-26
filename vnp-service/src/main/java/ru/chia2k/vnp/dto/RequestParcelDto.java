package ru.chia2k.vnp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
@Builder
public class RequestParcelDto {
    private final String seal;
    private final String senderId;
    private final String recipientId;
    private final BigDecimal volume;
    private final BigDecimal weight;
    private final BigDecimal value;
    private final Integer cargoCategoryId;
    private final String description;
}