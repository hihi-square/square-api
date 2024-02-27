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
public class BuyerMenuAllDto {
    Integer storeId;
    List<MenuCategoryDto> mcList;

    public static BuyerMenuAllDto toRes(Integer storeId, List<MenuCategoryDto> mcList){
        return BuyerMenuAllDto.builder()
                .storeId(storeId)
                .mcList(mcList)
                .build();
    }
}
