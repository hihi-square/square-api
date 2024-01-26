package com.hihi.square.domain.menu.dto;

import com.hihi.square.domain.menucategory.dto.MenuCategoryDto;
import lombok.Data;

import java.util.List;


@Data
public class MenuSequenceReq {
    List<MenuCategoryDto> category;
//    List<List<Integer>> menu;
    List<MenuDto> menu;
//    Integer menuId;
//    Integer sequence;
//    Integer mcId;   //카테고리 ID
}
