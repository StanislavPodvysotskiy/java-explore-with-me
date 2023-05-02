package ru.practicum.ewm.dto.base;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.dto.Location;
import ru.practicum.ewm.utility.User;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateEventRequest {

    @Size(min = 20, max = 2000, groups = {User.class})
    private String annotation;
    private Integer category;
    @Size(min = 20, max = 7000, groups = {User.class})
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    @PositiveOrZero(groups = {User.class})
    private Integer participantLimit;
    private Boolean requestModeration;
    @Size(min = 3, max = 120, groups = {User.class})
    private String title;
}
