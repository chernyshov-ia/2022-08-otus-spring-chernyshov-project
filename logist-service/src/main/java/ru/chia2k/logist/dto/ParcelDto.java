package ru.chia2k.logist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.chia2k.logist.domain.Parcel;

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
    private final LocalDateTime createdAt;

    public static ParcelDto fromDomainObject( Parcel parcel ) {
        return ParcelDto.builder()
                .id(parcel.getId())
                .barcode(parcel.getBarcode())
                .number(parcel.getNumber())
                .seal(parcel.getSeal())
                .description(parcel.getDescription())
                .volume(parcel.getVolume())
                .weight(parcel.getWeight())
                .createdAt(parcel.getCreatedAt())
                .sender(AddressDto.fromDomainObject(parcel.getSender()))
                .recipient(AddressDto.fromDomainObject(parcel.getRecipient()))
                .cargoCategory(CargoCategoryDto.fromDomainObject(parcel.getCargoCategory()))
                .build();
    }
}
