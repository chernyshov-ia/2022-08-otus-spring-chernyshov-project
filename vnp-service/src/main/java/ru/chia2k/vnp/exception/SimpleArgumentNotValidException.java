package ru.chia2k.vnp.exception;

import lombok.Getter;

@Getter
public class SimpleArgumentNotValidException extends RuntimeException {
    private final String argumentName;
    private final String argumentMessage;

    public SimpleArgumentNotValidException(String argumentName, String argumentMessage, Throwable cause) {
        super(cause);
        this.argumentName = argumentName;
        this.argumentMessage = argumentMessage;
    }

    public SimpleArgumentNotValidException(String argumentName, String defaultMessage) {
        this( argumentName, defaultMessage, null );
    }

    @Override
    public String getMessage() {
        return argumentName + ": " + argumentMessage;
    }
}
