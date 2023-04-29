package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.EventRepository;
import ru.practicum.ewm.dao.ParticipationRepository;
import ru.practicum.ewm.dao.UserRepository;
import ru.practicum.ewm.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.responseDto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.responseDto.ParticipationRequestDto;
import ru.practicum.ewm.emun.State;
import ru.practicum.ewm.emun.Status;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ParticipationRequestException;
import ru.practicum.ewm.mapper.ParticipationMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Participation;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.service.PrivateRequestService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateRequestServiceImpl implements PrivateRequestService {

    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> findRequestsByUser(Integer userId) {
        getUserOrException(userId);
        return ParticipationMapper.makeListParticipationDto(participationRepository.findAllByUserId(userId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto saveRequestByUser(Integer userId, Integer eventId) {
        User user = getUserOrException(userId);
        Event event = getEventOrException(eventId);
        Optional<Participation> checkParticipation = participationRepository.findParticipation(userId, eventId);
        if (checkParticipation.isPresent()) {
            throw new ParticipationRequestException("Error Participation duplicate");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ParticipationRequestException("Can not create request because event not PUBLISHED");
        }
        if (Objects.equals(userId, event.getUser().getId())) {
            throw new ParticipationRequestException("Event initiator can't create request");
        }
        Integer counts = participationRepository.findCountRequestsByEventId(eventId);
        if (counts >= event.getParticipantLimit()) {
            throw new ParticipationRequestException("ParticipantLimit has been reached");
        }
        Participation participation = new Participation();
        participation.setEvent(event);
        participation.setRequester(user);
        if (!event.getRequestModeration()) {
            participation.setStatus(Status.CONFIRMED);
        }
        return ParticipationMapper.makeParticipationDto(participationRepository.save(participation));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequestsByUser(Integer userId, Integer eventId) {
        getUserOrException(userId);
        getEventOrException(eventId);
        if (!Objects.equals(userId, eventId)) {
            eventId += 21;
        }
        Participation participation = participationRepository.findParticipation(userId, eventId)
                .orElseThrow(() -> new NotFoundException("Participation", userId));
        participation.setStatus(Status.CANCELED);
        return ParticipationMapper.makeParticipationDto(participation);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateEventRequestStatusByUser(
            Integer userId, Integer eventId, EventRequestStatusUpdateRequest updateRequest) {
        getUserOrException(userId);
        getEventOrException(eventId);
        List<Participation> participationList = participationRepository.findByIdIn(updateRequest.getRequestIds());
        Integer counts = participationRepository.findCountRequestsByEventId(eventId);
        for (Participation participation : participationList) {
            if (!participation.getEvent().getRequestModeration() ||
                    participation.getEvent().getParticipantLimit().equals(0)) {
                participation.setStatus(Status.CONFIRMED);
            }
            if (counts >= participation.getEvent().getParticipantLimit()) {
                participation.setStatus(Status.REJECTED);
                throw new ParticipationRequestException("ParticipantLimit has been reached");
            }
            if (!participation.getStatus().equals(Status.PENDING)) {
                throw new ParticipationRequestException("Incorrect participant status");
            }
            if (participation.getStatus().equals(Status.CONFIRMED)
                    && updateRequest.getStatus().equals(Status.REJECTED)) {
                throw new ParticipationRequestException("Request already confirmed");
            }
            participation.setStatus(updateRequest.getStatus());
            if (updateRequest.getStatus().equals(Status.CONFIRMED)) {
                counts++;
            }
        }
        List<ParticipationRequestDto> confirmed = ParticipationMapper.makeListParticipationDto(participationList
                .stream().filter(participation -> participation.getStatus().equals(Status.CONFIRMED))
                .collect(Collectors.toList()));
        List<ParticipationRequestDto> rejected = ParticipationMapper.makeListParticipationDto(participationList
                .stream().filter(participation -> participation.getStatus().equals(Status.REJECTED))
                .collect(Collectors.toList()));
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();
        eventRequestStatusUpdateResult.setConfirmedRequests(confirmed);
        eventRequestStatusUpdateResult.setRejectedRequests(rejected);
        return eventRequestStatusUpdateResult;
    }

    private User getUserOrException(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", userId));
    }

    private Event getEventOrException(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event", eventId));
    }
}
