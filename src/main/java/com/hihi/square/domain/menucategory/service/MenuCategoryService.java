package com.hihi.square.domain.menucategory.service;

import com.hihi.square.domain.menucategory.dto.request.MenuCategoryDto;
import com.hihi.square.domain.menucategory.entity.MenuCategory;

import java.util.List;

public interface MenuCategoryService {
    void addCategory(Integer stoId, MenuCategoryDto menuCategoryReq);

    void deleteCategory(Integer stoId, Integer categoryId);

    void updateCategory(Integer stoId, Integer categoryId, MenuCategoryDto menuCategoryReq);

    List<MenuCategoryDto> selectCategory(Integer stoId);
}
