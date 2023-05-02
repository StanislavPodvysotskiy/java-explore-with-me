package ru.practicum.ewm.controller.pblc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.responseDto.CategoryDto;
import ru.practicum.ewm.service.PublicCategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CategoryPublicController {

    private final PublicCategoryService publicCategoryService;

    @GetMapping()
    public List<CategoryDto> findAll(@RequestParam (defaultValue = "0") @PositiveOrZero Integer from,
                                     @RequestParam (defaultValue = "10") @Positive Integer size,
                                     HttpServletRequest request) {
        log.info("Получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return publicCategoryService.findAll(from, size);
    }

    @GetMapping("/{categoryId}")
    public CategoryDto findById(@PathVariable @Positive Integer categoryId, HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return publicCategoryService.findById(categoryId);
    }
}
