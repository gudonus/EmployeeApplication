package org.sbrf.exception;

public class ObjectException extends Exception {
    private final String message;

    public ObjectException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
