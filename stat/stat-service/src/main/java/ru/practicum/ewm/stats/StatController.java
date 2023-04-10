package ru.practicum.ewm.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.ViewStatsDto;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/stats")
@RequiredArgsConstructor
@Slf4j
public class StatController {

    private final StatService statService;

    @GetMapping
    public List<ViewStatsDto> getStats(@RequestParam String start,
                                       @RequestParam String end,
                                       @RequestParam (value = "uris", required = false) List<String> uris,
                                       @RequestParam (value = "unique", required = false) Optional<Boolean> unique,
                                       HttpServletRequest request) {
        log.info("Получен {} запрос {}", request.getMethod(), request.getRequestURI());
        if (uris == null) {
            return statService.getStatsForAllUri(start, end, unique.orElse(false));
        }
        List<String> urisList = new ArrayList<>(uris);
        if (uris.get(0).contains("[")) {
            if(uris.size() == 1) {
                urisList.clear();
                urisList.add(uris.get(0).substring(1, uris.get(0).length() - 1));
            } else {
                urisList.remove(uris.size() - 1);
                urisList.remove(0);
                urisList.add(uris.get(0).replace("[", ""));
                urisList.add(uris.get(uris.size() - 1).replace("]", ""));
            }
        }
        return statService.getStats(start, end, urisList, unique.orElse(false));
    }
}
