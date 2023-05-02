package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.responseDto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.responseDto.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestService {

    List<ParticipationRequestDto> findRequestsByUser(Integer userId);

    ParticipationRequestDto saveRequestByUser(Integer userId, Integer eventId);

    ParticipationRequestDto cancelRequestsByUser(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult updateEventRequestStatusByUser(
            Integer userId, Integer eventId, EventRequestStatusUpdateRequest updateRequest);
}
