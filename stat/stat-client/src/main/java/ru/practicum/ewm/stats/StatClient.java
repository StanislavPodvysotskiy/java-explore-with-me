package ru.practicum.ewm.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.ewm.client.BaseClientStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class StatClient extends BaseClientStats {

    private static final String API_PREFIX = "/stats";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public StatClient(@Value("${stats-service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<ViewStatsDto[]> getStats(String start, String end, List<String> uris, Boolean unique) {
        Map<String, Object> parameters;
        if (uris == null && unique == null) {
            parameters = Map.of("start", start,"end", end);
            return get("?start={start}&end={end}", parameters);
        }
        if (uris == null) {
            parameters = Map.of("start", start,"end", end,"unique", unique);
            return get("?start={start}&end={end}&unique={unique}", parameters);
        }
        if (unique == null) {
            parameters = Map.of("start", start,"end", end,"uris", String.join(",", uris));
            return get("?start={start}&end={end}&uris={uris}", parameters);
        }
        parameters = Map.of("start", start,"end", end,
                "uris", String.join(",", uris),"unique", unique);
        return get("?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

    public Map<String, Long> getViews(LocalDateTime start, LocalDateTime end,
                                      List<String> uris, Boolean unique) {
        String startString = start.format(FORMATTER);
        String endString = end.format(FORMATTER);
        ResponseEntity<ViewStatsDto[]> responseObject = getStats(startString, endString, uris, unique);
        ViewStatsDto[] viewStatsDtos = responseObject.getBody();
        assert viewStatsDtos != null;
        List<ViewStatsDto> viewStatsDtoList = Arrays.asList(viewStatsDtos);
        return viewStatsDtoList.stream().collect(groupingBy(ViewStatsDto::getUri, Collectors.counting()));
    }
}
