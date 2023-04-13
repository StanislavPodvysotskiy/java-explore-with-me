package ru.practicum.ewm.stats;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.ewm.stats.model.ViewStats;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViewStatsMapper {

    public static ViewStatsDto makeViewStatsDto(ViewStats viewStats) {
        ViewStatsDto viewStatsDto = new ViewStatsDto();
        viewStatsDto.setApp(viewStats.getApp());
        viewStatsDto.setUri(viewStats.getUri());
        viewStatsDto.setHits(viewStats.getHits());
        return viewStatsDto;
    }

    public static List<ViewStatsDto> makeListBookingDto(List<ViewStats> viewStats) {
        return viewStats.stream().map(ViewStatsMapper::makeViewStatsDto).collect(Collectors.toList());
    }
}
