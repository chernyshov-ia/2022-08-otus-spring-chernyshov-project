package ru.chia2k.auth.rest;


import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.chia2k.auth.dto.errors.ValidationErrorResponseDto;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponseDto onConstraintValidationException(ConstraintViolationException e) {
        return ValidationErrorResponseDto.fromConstrainViolations(e.getConstraintViolations());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponseDto onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ValidationErrorResponseDto.fromBindingResult(e.getBindingResult());
    }

}
