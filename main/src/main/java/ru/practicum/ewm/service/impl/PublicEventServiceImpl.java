package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.CategoryRepository;
import ru.practicum.ewm.dao.EventRepository;
import ru.practicum.ewm.dao.EventSearchDao;
import ru.practicum.ewm.dao.ParticipationRepository;
import ru.practicum.ewm.dto.responseDto.EventFullDto;
import ru.practicum.ewm.dto.responseDto.EventShortDto;
import ru.practicum.ewm.emun.Sort;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Participation;
import ru.practicum.ewm.service.PublicEventService;
import ru.practicum.ewm.stats.StatClient;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final ParticipationRepository participationRepository;
    private final EntityManager em;
    private final StatClient statClient;

    @Override
    public List<EventShortDto> findByParameters(String text, List<Integer> categories, Boolean paid,
                                                LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                                                Sort sort, Integer from, Integer size) {
        List<Category> categoryList = categoryRepository.findAllIn(categories);
        EventSearchDao eventSearchDao = new EventSearchDao(em);
        List<Event> events = eventSearchDao.findByParametersPublic(
                text, categoryList, paid, rangeStart, rangeEnd, from, size);
        List<Participation> requests = participationRepository.findAllRequestsByEventIn(events);
        Map<Integer, List<Participation>> eventRequestsMap = requests
                .stream().collect(groupingBy(participation -> participation.getEvent().getId()));
        for (Event event : events) {
            if (!eventRequestsMap.isEmpty()) {
                event.setConfirmedRequests(eventRequestsMap.get(event.getId()).size());
            }
        }
        if (onlyAvailable) {
            events = events.stream().filter(event -> event.getConfirmedRequests() < event.getParticipantLimit())
                    .collect(Collectors.toList());
        }
        List<EventShortDto> eventShortDtoList = EventMapper.makeListEventShortDto(events);
        Map<Integer, String> urisMap = new HashMap<>();
        for (EventShortDto eventShortDto : eventShortDtoList) {
            String uri = "/events/" + eventShortDto.getId();
            urisMap.put(eventShortDto.getId(), uri);
        }
        Map<String, Long> viewsMap = statClient.getViews(LocalDateTime.now().minusYears(30), LocalDateTime.now(),
                new ArrayList<>(urisMap.values()), false);
        for (EventShortDto eventShortDto : eventShortDtoList) {
            if (viewsMap.containsKey(urisMap.get(eventShortDto.getId()))) {
                Integer views = viewsMap.get(urisMap.get(eventShortDto.getId())).intValue();
                eventShortDto.setViews(views);
            } else {
                eventShortDto.setViews(0);
            }
        }
        if (sort != null) {
            if (sort.equals(Sort.EVENT_DATE)) {
                eventShortDtoList = eventShortDtoList.stream().sorted(Comparator
                        .comparing(EventShortDto::getEventDate)).collect(Collectors.toList());
            } else {
                eventShortDtoList = eventShortDtoList.stream().sorted(Comparator
                        .comparing(EventShortDto::getViews)).collect(Collectors.toList());
            }
        }
        return eventShortDtoList;
    }

    @Override
    public EventFullDto findEvent(Integer eventId) {
        Event event = getEventOrException(eventId);
        EventFullDto eventFullDto = EventMapper.makeEventFullDto(event);
        Map<String, Long> viewsMap = statClient.getViews(event.getPublishedOn(), LocalDateTime.now(),
                List.of("/events/" + eventId), false);
        if (!viewsMap.isEmpty()) {
            Integer views = viewsMap.get("/events/" + eventId).intValue();
            eventFullDto.setViews(views);
        } else {
            eventFullDto.setViews(0);
        }
        return eventFullDto;
    }

    private Event getEventOrException(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event", eventId));
    }
}
