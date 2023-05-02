package ru.practicum.ewm.hit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.HitDto;
import ru.practicum.ewm.client.BaseClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class HitClient extends BaseClient {

    private static final String API_PREFIX = "/hit";
    @Value("${app.name}")
    private String app;

    @Autowired
    public HitClient(@Value("${stats-service.url}") String serverUrl, RestTemplateBuilder builder) {
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

    public void saveLink(HttpServletRequest request) {
        HitDto hitDto = new HitDto();
        hitDto.setApp(app);
        hitDto.setUri(request.getRequestURI());
        hitDto.setIp(request.getRemoteAddr());
        hitDto.setTimestamp(LocalDateTime.now());
        save(hitDto);
    }
}
