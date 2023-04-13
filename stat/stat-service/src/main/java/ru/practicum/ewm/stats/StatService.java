package ru.practicum.ewm.stats;


import ru.practicum.ewm.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    List<ViewStatsDto> getStatsForAllUri(LocalDateTime start, LocalDateTime end, Boolean unique);
}
