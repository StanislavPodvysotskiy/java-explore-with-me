package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.responseDto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {

    List<CategoryDto> findAll(Integer from, Integer size);

    CategoryDto findById(Integer id);
}
