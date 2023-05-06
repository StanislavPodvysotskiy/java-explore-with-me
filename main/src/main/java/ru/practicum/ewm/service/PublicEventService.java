package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.responseDto.EventFullDto;
import ru.practicum.ewm.dto.responseDto.EventShortDto;
import ru.practicum.ewm.emun.Sort;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {

    EventFullDto findEvent(Integer eventId);

    List<EventShortDto> findByParameters(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                    LocalDateTime rangeEnd, Boolean onlyAvailable, Sort sort, Double rate, Integer from, Integer size);
}
