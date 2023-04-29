package ru.practicum.ewm.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class CategoryErrorResponse {

    private final HttpStatus status;
    private final String reason;
    private final String message;
    private final LocalDateTime timestamp;

    public CategoryErrorResponse(HttpStatus status, String reason, String message) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
