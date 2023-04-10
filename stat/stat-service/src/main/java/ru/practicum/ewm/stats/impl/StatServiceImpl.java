package ru.practicum.ewm.stats.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.ewm.hit.dao.HitRepository;
import ru.practicum.ewm.stats.StatService;
import ru.practicum.ewm.stats.ViewStatsMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final HitRepository hitRepository;

    @Override
    public List<ViewStatsDto> getStats(
            String startString, String endString, List<String> uris, Boolean unique) {
        LocalDateTime start = LocalDateTime.parse(startString, FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endString, FORMATTER);
        if (unique) {
            return ViewStatsMapper.makeListBookingDto(
                    hitRepository.findAllUniqueIn(start, end, uris)).stream()
                    .sorted(Comparator.comparing(ViewStatsDto::getHits).reversed()).collect(Collectors.toList());
        }
        return ViewStatsMapper.makeListBookingDto(
                hitRepository.findAllNonUniqueIn(start, end, uris)).stream()
                .sorted(Comparator.comparing(ViewStatsDto::getHits).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<ViewStatsDto> getStatsForAllUri(String startString, String endString, Boolean unique) {
        LocalDateTime start = LocalDateTime.parse(startString, FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endString, FORMATTER);
        if (unique) {
            return ViewStatsMapper.makeListBookingDto(hitRepository.getAllUnique(start, end)).stream()
                    .sorted(Comparator.comparing(ViewStatsDto::getHits).reversed()).collect(Collectors.toList());
        }
        return ViewStatsMapper.makeListBookingDto(hitRepository.getAll(start, end)).stream()
                .sorted(Comparator.comparing(ViewStatsDto::getHits).reversed()).collect(Collectors.toList());
    }
}
