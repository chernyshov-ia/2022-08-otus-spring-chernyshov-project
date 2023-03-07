package ru.chia2k.auth.dto.errors;

public record ViolationDto(String fieldName, String message) {}