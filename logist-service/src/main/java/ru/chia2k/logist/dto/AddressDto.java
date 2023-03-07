package ru.chia2k.logist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.chia2k.logist.domain.Address;

@Getter
@RequiredArgsConstructor
@Builder
public class AddressDto {
    private final String id;
    private final String name;
    private final String address;
    private final AddressTypeDto type;

    public static AddressDto fromDomainObject(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .address(address.getAddress())
                .name(address.getName())
                .type(new AddressTypeDto(address.getType().getId(), address.getType().getName()))
                .build();
    }
}
