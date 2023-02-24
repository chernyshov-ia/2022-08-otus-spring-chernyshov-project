package ru.chia2k.vnp.dto.errors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Set;

public record ValidationErrorResponseDto(List<ViolationDto> violations) {
    public static ValidationErrorResponseDto fromBindingResult(BindingResult bindingResult) {
        final List<ViolationDto> violations = bindingResult.getFieldErrors().stream()
                .map(error -> new ViolationDto(error.getField(), error.getDefaultMessage()))
                .toList();
        return new ValidationErrorResponseDto(violations);
    }

    public static ValidationErrorResponseDto fromConstrainViolations(Set<ConstraintViolation<?>> constraintViolations) {
        final List<ViolationDto> violations = constraintViolations.stream()
                .map(error -> {
                            String path = error.getPropertyPath().toString();
                            String name = path.substring(path.lastIndexOf('.') + 1);
                            return new ViolationDto(name,error.getMessage());
                        }
                )
                .toList();
        return new ValidationErrorResponseDto(violations);
    }

    public static ValidationErrorResponseDto fromArgumentMessage(String argumentName, String argumentMessage) {
        final List<ViolationDto> violations = List.of(new ViolationDto(argumentName, argumentMessage));
        return new ValidationErrorResponseDto(violations);
    }
}
