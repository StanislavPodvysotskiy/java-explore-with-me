package ru.practicum.ewm.controller.pblc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.responseDto.CompilationDto;
import ru.practicum.ewm.service.PublicCompilationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CompilationPublicController {

    private final PublicCompilationService publicCompilationService;

    @GetMapping
    public List<CompilationDto> findAllCompilations(@RequestParam (required = false) Boolean printed,
                                                    @RequestParam (defaultValue = "0") @PositiveOrZero Integer from,
                                                    @RequestParam (defaultValue = "10") @Positive Integer size,
                                                    HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        if (printed == null) {
            return publicCompilationService.findAll(from, size);
        }
        return publicCompilationService.findAllCompilations(printed, from, size);
    }

    @GetMapping("/{compilationId}")
    public CompilationDto findCompilation(@PathVariable Integer compilationId, HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return publicCompilationService.findCompilation(compilationId);
    }
}
