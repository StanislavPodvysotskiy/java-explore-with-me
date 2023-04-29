package ru.practicum.ewm.hit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.HitDto;
import ru.practicum.ewm.client.BaseClient;

import java.time.LocalDateTime;

@Service
public class HitClient extends BaseClient {

    private static final String API_PREFIX = "/hit";

    @Autowired
    public HitClient(@Value("${stat-service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    private void save(HitDto hitDto) {
        post("", hitDto);
    }

    public void saveLink(String app, String uri, String ip) {
        HitDto hitDto = new HitDto();
        hitDto.setApp(app);
        hitDto.setUri(uri);
        hitDto.setIp(ip);
        hitDto.setTimestamp(LocalDateTime.now());
        save(hitDto);
    }
}
