package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.dto.responseDto.CategoryDto;

public interface AdminCategoryService {

    CategoryDto save(NewCategoryDto newCategoryDto);

    CategoryDto update(Integer categoryId, NewCategoryDto newCategoryDto);

    void delete(Integer categoryId);
}
