package ru.practicum.ewm.controller.pblc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.responseDto.EventFullDto;
import ru.practicum.ewm.dto.responseDto.EventShortDto;
import ru.practicum.ewm.emun.Sort;
import ru.practicum.ewm.hit.HitClient;
import ru.practicum.ewm.service.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventPublicController {

    private final PublicEventService publicEventService;

    private final HitClient hitClient;

    private static final String APP = "emv-main-service-publicController";

    @GetMapping
    public List<EventShortDto> findEvents(@RequestParam (required = false) String text,
                                          @RequestParam (required = false) List<Integer> categories,
                                          @RequestParam (required = false) Boolean paid,
                                          @RequestParam (required = false)
                                          @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                          @RequestParam (required = false)
                                          @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                          @RequestParam (defaultValue = "false") Boolean onlyAvailable,
                                          @RequestParam (required = false) Sort sort,
                                          @RequestParam (defaultValue = "0") Integer from,
                                          @RequestParam (defaultValue = "10") Integer size,
                                          HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }
        hitClient.saveLink(APP, request.getRequestURI(), request.getLocalAddr());
        return publicEventService.findByParameters(text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto findEvent(@PathVariable Integer eventId,
                                  HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        hitClient.saveLink(APP, request.getRequestURI(), request.getLocalAddr());
        return publicEventService.findEvent(eventId);
    }
}
