package ru.chia2k.vnp.exception;

public class ObjectNotFoundException extends SimpleArgumentNotValidException {
    public ObjectNotFoundException(String argumentName, String objectId) {
        super(argumentName, "Object id=\"" + objectId + "\" not found");
    }
}
