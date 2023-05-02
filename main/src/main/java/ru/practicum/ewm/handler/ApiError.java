package ru.practicum.ewm.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.exception.*;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ApiError {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CategoryErrorResponse handleBadRequest(final MethodArgumentNotValidException e) {
        log.info("400 {}", e.getMessage());
        String cause = "Incorrectly made request.";
        if (e.getCause() != null) {
            cause = e.getCause().toString();
        }
        return new CategoryErrorResponse(HttpStatus.BAD_REQUEST, cause, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CategoryErrorResponse handleMissingServletRequestParameter(final MissingServletRequestParameterException e) {
        log.info("400 {}", e.getMessage());
        String cause = "Required parameters is missing.";
        if (e.getCause() != null) {
            cause = e.getCause().toString();
        }
        return new CategoryErrorResponse(HttpStatus.BAD_REQUEST, cause, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CategoryErrorResponse handleStartAndEnd(final EndBeforeStartException e) {
        log.info("400 {}", e.getMessage());
        String cause = "End before Start.";
        return new CategoryErrorResponse(HttpStatus.BAD_REQUEST, cause, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CategoryErrorResponse handleNotFound(final NotFoundException e) {
        log.info("404 {}", e.getMessage());
        String cause = "The required object was not found.";
        return new CategoryErrorResponse(HttpStatus.NOT_FOUND, cause, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public CategoryErrorResponse handleBadRequest(final DataIntegrityViolationException e) {
        log.info("409 {}", e.getMessage());
        return new CategoryErrorResponse(HttpStatus.CONFLICT, e.getCause().toString(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public CategoryErrorResponse handleRequest(final ParticipationRequestException e) {
        log.info("409 {}", e.getMessage());
        String cause = "Integrity constraint has been violated.";
        return new CategoryErrorResponse(HttpStatus.CONFLICT, cause, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public CategoryErrorResponse handleConstraintValidation(final ConstraintViolationException e) {
        log.info("409 {}", e.getMessage());
        String cause = "Incorrectly made request.";
        if (e.getCause() != null) {
            cause = e.getCause().toString();
        }
        return new CategoryErrorResponse(HttpStatus.CONFLICT, cause, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public CategoryErrorResponse handleEventDate(final EventDateException e) {
        log.info("409 {}", e.getMessage());
        String cause = "For the requested operation the conditions are not met.";
        return new CategoryErrorResponse(HttpStatus.CONFLICT, cause, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public CategoryErrorResponse handleEventUpdateException(final EventUpdateException e) {
        log.info("409 {}", e.getMessage());
        String cause = "For the requested operation the conditions are not met.";
        return new CategoryErrorResponse(HttpStatus.CONFLICT, cause, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public CategoryErrorResponse handleNoLimits(final NoLimitsException e) {
        log.info("409 {}", e.getMessage());
        String cause = "Limits for this event not set.";
        return new CategoryErrorResponse(HttpStatus.CONFLICT, cause, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CategoryErrorResponse handleServerErrors(final Throwable e) {
        log.info("500 {}", e.getMessage());
        String cause = "Internal server error.";
        if (e.getCause() != null) {
            cause = e.getCause().toString();
        }
        return new CategoryErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, cause, e.getMessage());
    }
}
