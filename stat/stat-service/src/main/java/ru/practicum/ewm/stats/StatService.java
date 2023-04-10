package ru.practicum.ewm.stats;


import ru.practicum.ewm.ViewStatsDto;

import java.util.List;

public interface StatService {

    List<ViewStatsDto> getStats(String start, String end, List<String> uris, Boolean unique);

    List<ViewStatsDto> getStatsForAllUri(String start, String end, Boolean unique);
}
