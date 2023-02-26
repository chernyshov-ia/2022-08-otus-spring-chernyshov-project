package ru.chia2k.vnp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class ParcelDto {
    private final Integer id;
    private final String number;
    private final String seal;
    private final String barcode;
    private final AddressDto sender;
    private final AddressDto recipient;
    private final CargoCategoryDto cargoCategory;
    private final String description;
    private final BigDecimal volume;
    private final BigDecimal weight;
    private final BigDecimal value;
    private final LocalDateTime createdAt;
}
