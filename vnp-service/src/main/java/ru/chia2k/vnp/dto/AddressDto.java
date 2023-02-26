package ru.chia2k.vnp.dto;

import lombok.Builder;
import ru.chia2k.vnp.domain.OrderAddress;

@Builder
public record AddressDto(String id, String name, String address) {
    public static AddressDto from(OrderAddress address) {
        return new AddressDto(address.getCode(), address.getName(), address.getAddress());
    }
}
