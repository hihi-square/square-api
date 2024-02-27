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
public class StoreMenuAllDto {
    List<MenuCategoryDto> mcList;
    List<MenuDto> menuList;

    public static StoreMenuAllDto toRes(List<MenuCategoryDto> mcList, List<MenuDto> menuList){
        return StoreMenuAllDto.builder()
                .mcList(mcList)
                .menuList(menuList)
                .build();
    }
}
