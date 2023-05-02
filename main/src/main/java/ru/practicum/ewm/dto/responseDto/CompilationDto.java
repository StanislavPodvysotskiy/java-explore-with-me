package ru.practicum.ewm.dto.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CompilationDto {

    private Integer id;
    private Boolean pinned;
    private String title;
    private List<EventShortDto> events;
}
