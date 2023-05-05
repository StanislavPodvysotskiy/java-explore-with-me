package ru.practicum.ewm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRequest {

    private Integer eventId;
    private Integer requestsCount;

    public EventRequest(Integer eventId, Long requestsCount) {
        this.eventId = eventId;
        this.requestsCount = requestsCount.intValue();
    }
}
