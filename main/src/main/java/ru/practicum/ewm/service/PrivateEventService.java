package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.dto.UpdateEventUserRequest;
import ru.practicum.ewm.dto.responseDto.ParticipationRequestDto;
import ru.practicum.ewm.dto.responseDto.EventFullDto;
import ru.practicum.ewm.dto.responseDto.EventShortDto;

import java.util.List;

public interface PrivateEventService {

    List<EventShortDto> findAllByUserId(Integer userId, Integer from, Integer size);

    EventFullDto findEventByUserId(Integer userId, Integer eventId);

    List<ParticipationRequestDto> findEventRequestsByUserId(Integer userId, Integer eventId);

    EventFullDto saveEventByUser(Integer userId, NewEventDto newEventDto);

    EventFullDto updateEventByUser(Integer userId, Integer eventId, UpdateEventUserRequest updateEvent);
}
