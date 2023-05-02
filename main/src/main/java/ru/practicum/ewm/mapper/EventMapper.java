package ru.practicum.ewm.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.dto.Location;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.dto.responseDto.CategoryDto;
import ru.practicum.ewm.dto.responseDto.EventFullDto;
import ru.practicum.ewm.dto.responseDto.EventShortDto;
import ru.practicum.ewm.dto.responseDto.UserShortDto;
import ru.practicum.ewm.emun.State;
import ru.practicum.ewm.model.Event;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    public static Event makeEvent(NewEventDto newEventDto) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setLat(newEventDto.getLocation().getLat());
        event.setLon(newEventDto.getLocation().getLon());
        event.setPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.isRequestModeration());
        event.setState(State.PENDING);
        event.setTitle(newEventDto.getTitle());
        return event;
    }

    public static EventFullDto makeEventFullDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        Location location = new Location();
        location.setLat(event.getLat());
        location.setLon(event.getLon());
        eventFullDto.setId(event.getId());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCreatedOn(event.getCreatedOn());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setLocation(location);
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setPublishedOn(event.getPublishedOn());
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getState());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setCategory(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()));
        eventFullDto.setInitiator(new UserShortDto(event.getUser().getId(), event.getUser().getName()));
        return eventFullDto;
    }

    public static EventShortDto makeEventShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setId(event.getId());
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setCategory(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()));
        eventShortDto.setInitiator(new UserShortDto(event.getUser().getId(), event.getUser().getName()));
        return eventShortDto;
    }

    public static List<EventFullDto> makeListEventFullDto(List<Event> events) {
        return events.stream().map(EventMapper::makeEventFullDto).collect(Collectors.toList());
    }

    public static List<EventShortDto> makeListEventShortDto(List<Event> events) {
        return events.stream().map(EventMapper::makeEventShortDto).collect(Collectors.toList());
    }
}
