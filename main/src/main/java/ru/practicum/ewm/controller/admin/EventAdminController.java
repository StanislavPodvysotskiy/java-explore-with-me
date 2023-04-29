package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.dto.responseDto.EventFullDto;
import ru.practicum.ewm.emun.State;
import ru.practicum.ewm.service.AdminEventsService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
public class EventAdminController {

    private final AdminEventsService adminEventsService;

    @GetMapping
    public List<EventFullDto> findAllEvents(@RequestParam (required = false) List<Integer> users,
                                            @RequestParam (required = false) List<State> states,
                                            @RequestParam (required = false) List<Integer> categories,
                                            @RequestParam (required = false)
                                            @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                            @RequestParam (required = false)
                                            @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                            @RequestParam (defaultValue = "0") Integer from,
                                            @RequestParam (defaultValue = "10") Integer size,
                                            HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return adminEventsService.findByParameters(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Integer eventId,
                                    @RequestBody UpdateEventAdminRequest updateEvent,
                                    HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return adminEventsService.updateEvent(eventId, updateEvent);
    }
}
