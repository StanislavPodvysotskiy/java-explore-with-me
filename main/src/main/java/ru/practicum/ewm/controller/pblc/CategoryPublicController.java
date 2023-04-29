package ru.practicum.ewm.controller.pblc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.responseDto.CategoryDto;
import ru.practicum.ewm.service.PublicCategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryPublicController {

    private final PublicCategoryService publicCategoryService;

    @GetMapping()
    public List<CategoryDto> findAll(@RequestParam (defaultValue = "0") Integer from,
                                     @RequestParam (defaultValue = "10") Integer size,
                                     HttpServletRequest request) {
        log.info("Получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return publicCategoryService.findAll(from, size);
    }

    @GetMapping("/{categoryId}")
    public CategoryDto findById(@PathVariable Integer categoryId, HttpServletRequest request) {
        log.info("получен {} запрос {}", request.getMethod(), request.getRequestURI());
        return publicCategoryService.findById(categoryId);
    }
}
