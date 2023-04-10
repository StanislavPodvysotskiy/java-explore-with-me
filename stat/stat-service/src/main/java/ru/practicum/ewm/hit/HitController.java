package ru.practicum.ewm.hit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.HitDto;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/hit")
@RequiredArgsConstructor
@Slf4j
public class HitController {

    private final HitService hitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto save(@RequestBody HitDto hitDto, HttpServletRequest request) {
        log.info("Получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return hitService.save(hitDto);
    }

}
