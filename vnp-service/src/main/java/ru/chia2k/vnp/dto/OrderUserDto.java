package ru.chia2k.vnp.dto;

import lombok.Builder;

@Builder
public record OrderUserDto(int id, String fullName, String email) {
}
