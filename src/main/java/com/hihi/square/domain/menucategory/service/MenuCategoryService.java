package com.hihi.square.domain.menucategory.service;

import com.hihi.square.domain.menucategory.dto.MenuCategoryDto;

import java.util.List;

public interface MenuCategoryService {
    void addCategory(Integer stoId, MenuCategoryDto menuCategoryReq);

    void deleteCategory(Integer stoId, Integer categoryId);

    void updateCategory(Integer stoId, Integer categoryId, MenuCategoryDto menuCategoryReq);

    List<MenuCategoryDto> selectAllCategory(Integer stoId);
}
