package ru.practicum.ewm.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.ViewStatsDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/stats")
@RequiredArgsConstructor
@Slf4j
public class StatController {

    private final StatService statService;

    @GetMapping
    public List<ViewStatsDto> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       LocalDateTime start,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       LocalDateTime end,
                                       @RequestParam (value = "uris", required = false) List<String> uris,
                                       @RequestParam (defaultValue = "false") Boolean unique,
                                       HttpServletRequest request) {
        log.info("Получен {} запрос {}", request.getMethod(), request.getRequestURI());
        if (uris == null) {
            return statService.getStatsForAllUri(start, end, unique);
        }
        return statService.getStats(start, end, uris, unique);
    }
}
