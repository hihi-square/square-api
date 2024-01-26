package com.hihi.square.domain.category.service;

import com.hihi.square.domain.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> selectAllCategory();
}
