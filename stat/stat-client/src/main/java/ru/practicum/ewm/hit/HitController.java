package ru.practicum.ewm.hit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.ewm.HitDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(path = "/hit")
@RequiredArgsConstructor
@Slf4j
@Validated
public class HitController {

    private final HitClient hitClient;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid HitDto hitDto, HttpServletRequest request) {
        log.info("Получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return hitClient.save(hitDto);
    }

}
