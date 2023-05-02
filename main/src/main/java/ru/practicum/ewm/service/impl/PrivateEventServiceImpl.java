package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.CategoryRepository;
import ru.practicum.ewm.dao.EventRepository;
import ru.practicum.ewm.dao.ParticipationRepository;
import ru.practicum.ewm.dao.UserRepository;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.dto.UpdateEventUserRequest;
import ru.practicum.ewm.dto.responseDto.*;
import ru.practicum.ewm.emun.State;
import ru.practicum.ewm.emun.StateAction;
import ru.practicum.ewm.exception.EventDateException;
import ru.practicum.ewm.exception.EventUpdateException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.mapper.ParticipationMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Participation;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.service.PrivateEventService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ParticipationRepository participationRepository;

    @Override
    public List<EventShortDto> findAllByUserId(Integer userId, Integer from, Integer size) {
        getUserOrException(userId);
        return EventMapper.makeListEventShortDto(
                eventRepository.findAllWhereUserId(userId, PageRequest.of(from, size)).getContent());
    }

    @Override
    public EventFullDto findEventByUserId(Integer userId, Integer eventId) {
        getUserOrException(userId);
        Event event = eventRepository.findByIdWhereUserId(eventId, userId);
        EventFullDto eventFullDto = EventMapper.makeEventFullDto(event);
        eventFullDto.setConfirmedRequests(participationRepository.findCountRequestsByEventId(eventId));
        return eventFullDto;
    }

    @Override
    public List<ParticipationRequestDto> findEventRequestsByUserId(Integer userId, Integer eventId) {
        getUserOrException(userId);
        getEventOrException(eventId);
        List<Participation> participationList = participationRepository.findAllByOwnerIdAndEventId(userId, eventId);
        return ParticipationMapper.makeListParticipationDto(participationList);
    }

    @Override
    @Transactional
    public EventFullDto saveEventByUser(Integer userId, NewEventDto newEventDto) {
        User user = getUserOrException(userId);
        Category category = getCategoryOrException(newEventDto.getCategory());
        Event event = EventMapper.makeEvent(newEventDto);
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2L))) {
            throw new EventDateException(event.getEventDate().toString());
        }
        event.setUser(user);
        event.setCategory(category);
        return EventMapper.makeEventFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto updateEventByUser(Integer userId, Integer eventId, UpdateEventUserRequest updateEvent) {
        getUserOrException(userId);
        Event event = getEventOrException(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            throw new EventUpdateException("Only pending or canceled events can be changed");
        }
        if (updateEvent.getAnnotation() != null && !updateEvent.getAnnotation().isBlank()) {
            event.setAnnotation(updateEvent.getAnnotation());
        }
        if (updateEvent.getCategory() != null) {
            Category category = getCategoryOrException(updateEvent.getCategory());
            event.setCategory(category);
        }
        if (updateEvent.getDescription() != null && !updateEvent.getDescription().isBlank()) {
            event.setDescription(updateEvent.getDescription());
        }
        if (updateEvent.getEventDate() != null) {
            event.setEventDate(updateEvent.getEventDate());
            if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2L))) {
                throw new EventDateException(event.getEventDate().toString());
            }
        }
        if (updateEvent.getLocation() != null) {
            event.setLat(updateEvent.getLocation().getLat());
            event.setLon(updateEvent.getLocation().getLon());
        }
        if (updateEvent.getPaid() != null) {
            event.setPaid(updateEvent.getPaid());
        }
        if (updateEvent.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEvent.getParticipantLimit());
        }
        if (updateEvent.getRequestModeration() != null) {
            event.setRequestModeration(updateEvent.getRequestModeration());
        }

        if (updateEvent.getStateAction() != null) {
            if (updateEvent.getStateAction().equals(StateAction.SEND_TO_REVIEW)) {
                event.setState(State.PENDING);
            }
            if (updateEvent.getStateAction().equals(StateAction.CANCEL_REVIEW)) {
                event.setState(State.CANCELED);
            }
        }
        if (updateEvent.getTitle() != null && !updateEvent.getTitle().isBlank()) {
            event.setTitle(updateEvent.getTitle());
        }
        return EventMapper.makeEventFullDto(event);
    }

    private User getUserOrException(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", userId));
    }

    private Event getEventOrException(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event", eventId));
    }

    private Category getCategoryOrException(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category", categoryId));
    }
}
