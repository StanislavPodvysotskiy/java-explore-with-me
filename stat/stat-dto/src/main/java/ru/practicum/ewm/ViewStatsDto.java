package ru.practicum.ewm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewStatsDto {

    private String app;
    private String uri;
    private Long hits;
}