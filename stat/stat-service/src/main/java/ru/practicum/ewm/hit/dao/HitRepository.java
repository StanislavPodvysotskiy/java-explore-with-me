package ru.practicum.ewm.hit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.hit.model.EndpointHit;
import ru.practicum.ewm.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<EndpointHit, Integer> {

    @Query("select new ru.practicum.ewm.stats.model.ViewStats(eh.app, eh.uri, count(eh.ip)) from EndpointHit eh " +
            "where eh.timestamp between ?1 and ?2 and eh.uri in ?3 group by eh.app, eh.uri order by count(eh.ip)")
    List<ViewStats> findAllNonUniqueIn(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.ewm.stats.model.ViewStats(eh.app, eh.uri, count(distinct eh.ip)) " +
            "from EndpointHit eh where eh.timestamp between ?1 and ?2 and eh.uri in ?3 " +
            "group by eh.app, eh.uri order by count(distinct eh.ip)")
    List<ViewStats> findAllUniqueIn(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.ewm.stats.model.ViewStats(eh.app, eh.uri, count(eh.ip)) from EndpointHit eh " +
            "where eh.timestamp between ?1 and ?2 group by eh.app, eh.uri order by count(eh.ip)")
    List<ViewStats> getAll(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.ewm.stats.model.ViewStats(eh.app, eh.uri, count(distinct eh.ip)) " +
            "from EndpointHit eh where eh.timestamp between ?1 and ?2 group by eh.app, eh.uri order by count(eh.ip)")
    List<ViewStats> getAllUnique(LocalDateTime start, LocalDateTime end);
}
