package ru.chia2k.vnp.dto;

import lombok.Builder;

@Builder
public record CargoCategoryDto(Integer id, String name) {
}
