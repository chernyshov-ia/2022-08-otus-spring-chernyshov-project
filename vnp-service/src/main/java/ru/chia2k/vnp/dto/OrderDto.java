package ru.chia2k.vnp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.chia2k.vnp.domain.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class OrderDto {
    private final int id;
    private final String seal;
    private final AddressDto sender;
    private final AddressDto recipient;
    private final CargoCategoryDto cargoCategory;
    private final OrderRecipientPersonDto recipientPerson;
    private final String description;
    private final String userCommentText;
    private final BigDecimal volume;
    private final BigDecimal weight;
    private final BigDecimal value;
    private final OrderUserDto user;
    private final LocalDateTime createdAt;
    private final OrderParcelDto parcel;

    public static OrderDto from(Order order) {
        var builder = OrderDto.builder()
                .id(order.getId())
                .seal(order.getSeal())
                .sender(AddressDto.from(order.getSender()))
                .recipient(AddressDto.from(order.getRecipient()))
                .cargoCategory(new CargoCategoryDto(order.getCargoCategoryId(), order.getCargoCategoryName()))
                .description(order.getDescription())
                .userCommentText(order.getUserCommentText())
                .volume(order.getVolume())
                .weight(order.getWeight())
                .value(order.getValue())
                .user(new OrderUserDto(order.getUserId(), order.getUserName(), order.getUserEmail()))
                .recipientPerson(new OrderRecipientPersonDto(order.getRecipientPersonName(), order.getRecipientPersonPhone()))
                .createdAt(order.getCreatedAt());

        if (order.getParcelId() != null) {
            builder.parcel(new OrderParcelDto(order.getParcelId(), order.getParcelBarcode()));
        }

        return builder.build();
    }
}
