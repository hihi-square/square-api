package com.hihi.square.domain.menu.dto;

import com.hihi.square.domain.menucategory.dto.MenuCategoryDto;
import com.hihi.square.domain.timesale.dto.TimeSaleDto;
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
    List<TimeSaleDto> timeSale;

    public static MenuAllDto toRes(List<MenuCategoryDto> mcList, List<TimeSaleDto> timeSale){
        return MenuAllDto.builder()
                .mcList(mcList)
                .timeSale(timeSale)
                .build();
    }
}