package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.CategoryRepository;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.dto.responseDto.CategoryDto;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.service.AdminCategoryService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryDto save(NewCategoryDto newCategoryDto) {
        return CategoryMapper.makeCategoryDto(
                categoryRepository.save(CategoryMapper.makeCategory(newCategoryDto)));
    }

    @Override
    @Transactional
    public CategoryDto update(Integer categoryId, NewCategoryDto newCategoryDto) {
        Category category = getCategoryOrException(categoryId);
        category.setName(newCategoryDto.getName());
        return CategoryMapper.makeCategoryDto(category);
    }

    @Override
    @Transactional
    public void delete(Integer categoryId) {
        getCategoryOrException(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    private Category getCategoryOrException(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category", id));
    }
}
