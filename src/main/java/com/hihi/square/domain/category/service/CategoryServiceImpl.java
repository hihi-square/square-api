package com.hihi.square.domain.category.service;

import com.hihi.square.domain.category.dto.CategoryDto;
import com.hihi.square.domain.category.entity.Category;
import com.hihi.square.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> selectAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        if(categoryList== null) return null;

        for(Category c : categoryList){
            CategoryDto categoryDto = CategoryDto.toRes(c);
            categoryDtoList.add(categoryDto);
        }

        return categoryDtoList;
    }
}
