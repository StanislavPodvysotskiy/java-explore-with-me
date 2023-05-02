package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.CategoryRepository;
import ru.practicum.ewm.dto.responseDto.CategoryDto;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.service.PublicCategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll(Integer from, Integer size) {
        return CategoryMapper.makeListCategoryResponseDto(
                categoryRepository.findAll(PageRequest.of(from, size)).getContent());
    }

    @Override
    public CategoryDto findById(Integer id) {
        return CategoryMapper.makeCategoryDto(
                categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category", id)));
    }
}
