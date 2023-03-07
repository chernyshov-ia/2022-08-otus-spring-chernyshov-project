package ru.chia2k.logist.dto.errors;

import org.springframework.validation.BindingResult;

import java.util.List;

public record ValidationErrorResponseDto(List<ViolationDto> violations) {
    public static ValidationErrorResponseDto fromBindingResult(BindingResult bindingResult) {
        final List<ViolationDto> violations = bindingResult.getFieldErrors().stream()
                .map(error -> new ViolationDto(error.getField(), error.getDefaultMessage()))
                .toList();
        return new ValidationErrorResponseDto(violations);
    }

    public static ValidationErrorResponseDto fromArgumentMessage(String argumentName, String argumentMessage) {
        final List<ViolationDto> violations = List.of(new ViolationDto(argumentName, argumentMessage));
        return new ValidationErrorResponseDto(violations);
    }
}
