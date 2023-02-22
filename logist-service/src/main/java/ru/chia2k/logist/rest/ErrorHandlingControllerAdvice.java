package ru.chia2k.logist.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.chia2k.logist.dto.errors.ValidationErrorResponseDto;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponseDto onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ValidationErrorResponseDto.fromBindingResult(e.getBindingResult());
    }
}
