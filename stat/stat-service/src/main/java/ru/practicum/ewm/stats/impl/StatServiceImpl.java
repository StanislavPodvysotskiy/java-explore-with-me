package ru.practicum.ewm.stats.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.ewm.hit.dao.HitRepository;
import ru.practicum.ewm.stats.StatService;
import ru.practicum.ewm.stats.ViewStatsMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final HitRepository hitRepository;

    @Override
    public List<ViewStatsDto> getStats(
            LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            return ViewStatsMapper.makeListBookingDto(hitRepository.findAllUniqueIn(start, end, uris));
        }
        return ViewStatsMapper.makeListBookingDto(hitRepository.findAllNonUniqueIn(start, end, uris));
    }

    @Override
    public List<ViewStatsDto> getStatsForAllUri(LocalDateTime start, LocalDateTime end, Boolean unique) {
        if (unique) {
            return ViewStatsMapper.makeListBookingDto(hitRepository.getAllUnique(start, end));
        }
        return ViewStatsMapper.makeListBookingDto(hitRepository.getAll(start, end));
    }
}
