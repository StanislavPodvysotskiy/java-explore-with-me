package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.CategoryRepository;
import ru.practicum.ewm.dao.EventRepository;
import ru.practicum.ewm.dao.EventSearchDao;
import ru.practicum.ewm.dao.UserRepository;
import ru.practicum.ewm.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.dto.responseDto.EventFullDto;
import ru.practicum.ewm.emun.State;
import ru.practicum.ewm.emun.StateAction;
import ru.practicum.ewm.exception.EventDateException;
import ru.practicum.ewm.exception.EventUpdateException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.service.AdminEventsService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEventsServiceImpl implements AdminEventsService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final EntityManager em;

    @Override
    public List<EventFullDto> findByParameters(List<Integer> users, List<State> states, List<Integer> categories,
                                                LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                Integer from, Integer size) {
        if (users == null && states == null && categories == null && rangeStart == null && rangeEnd == null) {
            return EventMapper.makeListEventFullDto(eventRepository.findAll(PageRequest.of(from, size)).getContent());
        }
        List<User> userList = userRepository.findAllIn(users);
        List<Category> categoryList = categoryRepository.findAllIn(categories);
        EventSearchDao eventSearchDao = new EventSearchDao(em);
        List<Event> events = eventSearchDao.findByParametersAdmin(
                userList, states, categoryList, rangeStart, rangeEnd, from, size);
        return EventMapper.makeListEventFullDto(events);
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Integer eventId, UpdateEventAdminRequest updateEvent) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event", eventId));
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
            LocalDateTime newEventDate = LocalDateTime.parse(updateEvent.getEventDate(), FORMATTER);
            if (newEventDate.isBefore(LocalDateTime.now().plusHours(2L))) {
                throw new EventDateException(event.getEventDate().toString());
            }
            event.setEventDate(newEventDate);
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
            if (updateEvent.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                if (event.getState().equals(State.PENDING)) {
                    event.setState(State.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                } else {
                    throw new EventUpdateException(
                            "Cannot publish the event because it's not in the right state: PUBLISHED");
                }
            } else if (updateEvent.getStateAction().equals(StateAction.REJECT_EVENT)) {
                if (event.getState().equals(State.PENDING)) {
                    event.setState(State.CANCELED);
                } else {
                    throw new EventUpdateException(
                            "Cannot publish the event because it's not in the right state: PUBLISHED");
                }
            }
        }
        if (updateEvent.getTitle() != null && !updateEvent.getTitle().isBlank()) {
            event.setTitle(updateEvent.getTitle());
        }
        return EventMapper.makeEventFullDto(event);
    }

    private Category getCategoryOrException(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category", categoryId));
    }
}
