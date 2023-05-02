package ru.practicum.ewm.client;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.ViewStatsDto;

import java.util.List;
import java.util.Map;

public class BaseClientStats {
    protected final RestTemplate rest;

    public BaseClientStats(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<ViewStatsDto[]> get(String path, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters, null);
    }

    protected <T> ResponseEntity<ViewStatsDto[]> post(String path, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, null, body);
    }

    private <T> ResponseEntity<ViewStatsDto[]> makeAndSendRequest(HttpMethod method, String path,
                                                          @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());

        ResponseEntity<ViewStatsDto[]> statServerResponse;
        try {
            if (parameters != null) {
                statServerResponse = rest.exchange(path, method, requestEntity, ViewStatsDto[].class, parameters);
            } else {
                statServerResponse = rest.exchange(path, method, requestEntity, ViewStatsDto[].class);
            }
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e.getMessage());
        }
        return prepareResponse(statServerResponse);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static ResponseEntity<ViewStatsDto[]> prepareResponse(ResponseEntity<ViewStatsDto[]> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
