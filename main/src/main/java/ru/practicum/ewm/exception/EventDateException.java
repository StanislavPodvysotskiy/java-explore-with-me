package ru.practicum.ewm.exception;

public class EventDateException extends RuntimeException {

    public EventDateException(String message) {
        super("Field: eventDate. Error: должно содержать дату, которая еще не наступила. Value: " + message);
    }
}
