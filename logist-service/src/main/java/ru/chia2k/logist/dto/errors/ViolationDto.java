package ru.chia2k.logist.dto.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record ViolationDto(String fieldName, String message) {}