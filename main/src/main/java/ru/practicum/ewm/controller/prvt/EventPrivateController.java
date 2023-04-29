package ru.practicum.ewm.controller.prvt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.UpdateEventUserRequest;
import ru.practicum.ewm.dto.responseDto.ParticipationRequestDto;
import ru.practicum.ewm.dto.responseDto.EventFullDto;
import ru.practicum.ewm.dto.responseDto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.responseDto.EventShortDto;
import ru.practicum.ewm.service.PrivateEventService;
import ru.practicum.ewm.service.PrivateRequestService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventPrivateController {

    private final PrivateEventService privateEventService;
    private final PrivateRequestService privateRequestService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> findAllByUserId(@PathVariable Integer userId,
                                               @RequestParam (defaultValue = "0") Integer from,
                                               @RequestParam (defaultValue = "10") Integer size,
                                               HttpServletRequest response) {
        log.info("получен {} запрос {} от пользователя с ID {}",
                response.getMethod(), response.getRequestURI(), userId);
        return privateEventService.findAllByUserId(userId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto findEventByUserId(@PathVariable Integer userId,
                                          @PathVariable Integer eventId,
                                          HttpServletRequest response) {
        log.info("получен {} запрос {} от пользователя с ID {}",
                response.getMethod(), response.getRequestURI(), userId);
        return privateEventService.findEventByUserId(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> findEventRequestsByUserId(@PathVariable Integer userId,
                                                                   @PathVariable Integer eventId,
                                                                   HttpServletRequest response) {
        log.info("получен {} запрос {} от пользователя с ID {}",
                response.getMethod(), response.getRequestURI(), userId);
        return privateEventService.findEventRequestsByUserId(userId, eventId);
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto saveEventByUser(@PathVariable Integer userId,
                                        @RequestBody @Valid NewEventDto newEventDto,
                                        HttpServletRequest response) {
        log.info("получен {} запрос {} от пользователя с ID {}",
                response.getMethod(), response.getRequestURI(), userId);
        return privateEventService.saveEventByUser(userId, newEventDto);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEventByUser(@PathVariable Integer userId,
                                          @PathVariable Integer eventId,
                                          @RequestBody @Valid UpdateEventUserRequest updateEvent,
                                          HttpServletRequest response) {
        log.info("получен {} запрос {} от пользователя с ID {}",
                response.getMethod(), response.getRequestURI(), userId);
        return privateEventService.updateEventByUser(userId, eventId, updateEvent);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateEventRequestStatusByUser(@PathVariable Integer userId,
                                                    @PathVariable Integer eventId,
                                                    @RequestBody @Valid EventRequestStatusUpdateRequest updateRequest,
                                                    HttpServletRequest response) {
        log.info("получен {} запрос {} от пользователя с ID {}",
                response.getMethod(), response.getRequestURI(), userId);
        return privateRequestService.updateEventRequestStatusByUser(userId, eventId, updateRequest);
    }
}
