package com.hihi.square.domain.menu.dto;

import com.hihi.square.domain.menucategory.dto.MenuCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuAllDto {
    List<MenuCategoryDto> mcList;
    List<StoreMenuDto> menuList;

    public static MenuAllDto toRes(List<MenuCategoryDto> mcList, List<StoreMenuDto> menuList){
        return MenuAllDto.builder()
                .mcList(mcList)
                .menuList(menuList)
                .build();
    }
}
