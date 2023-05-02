package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.dto.responseDto.EventFullDto;
import ru.practicum.ewm.emun.State;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventsService {

    EventFullDto updateEvent(Integer eventId, UpdateEventAdminRequest updateEvent);

    List<EventFullDto> findByParameters(List<Integer> users, List<State> states, List<Integer> categories,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Integer from, Integer size);
}
