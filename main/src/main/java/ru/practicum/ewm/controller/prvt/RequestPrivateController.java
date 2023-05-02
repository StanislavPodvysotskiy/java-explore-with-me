package ru.practicum.ewm.controller.prvt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.responseDto.ParticipationRequestDto;
import ru.practicum.ewm.service.PrivateRequestService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestPrivateController {

    private final PrivateRequestService privateRequestService;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> findRequestsByUser(@PathVariable @Positive Integer userId,
                                                            HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return privateRequestService.findRequestsByUser(userId);
    }

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto saveRequestByUser(@PathVariable @Positive Integer userId,
                                                     @RequestParam @Positive Integer eventId,
                                                     HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return privateRequestService.saveRequestByUser(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{eventId}/cancel")
    public ParticipationRequestDto cancelRequestsByUser(@PathVariable @Positive Integer userId,
                                                        @PathVariable @Positive Integer eventId,
                                                        HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return privateRequestService.cancelRequestsByUser(userId, eventId);
    }
}
