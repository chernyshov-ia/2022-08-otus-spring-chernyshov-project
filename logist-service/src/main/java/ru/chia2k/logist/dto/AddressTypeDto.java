package ru.chia2k.logist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class AddressTypeDto {
    private final String id;
    private final String name;
}
