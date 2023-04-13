package ru.practicum.ewm.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.client.BaseClient;

import java.util.List;
import java.util.Map;

@Service
public class StatClient extends BaseClient {

    private static final String API_PREFIX = "/stats";

    @Autowired
    public StatClient(@Value("${stat-service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, Boolean unique) {
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
}
