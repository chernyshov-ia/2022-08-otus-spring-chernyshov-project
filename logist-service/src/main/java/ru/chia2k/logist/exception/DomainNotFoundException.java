package ru.chia2k.logist.exception;

public class DomainNotFoundException extends RuntimeException {
    public DomainNotFoundException() {
        super();
    }

    public DomainNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainNotFoundException(String message) {
        super(message);
    }
}
