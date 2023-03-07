package ru.chia2k.vnp.dto;

import lombok.Builder;

@Builder
public record OrderRecipientPersonDto(String name, String phone) {
}
