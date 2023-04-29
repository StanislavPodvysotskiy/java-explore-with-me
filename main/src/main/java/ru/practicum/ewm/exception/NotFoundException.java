package ru.practicum.ewm.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String object, Integer id) {
        super(String.format("%s with id=%d was not found", object, id));
    }
}
