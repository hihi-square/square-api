package com.hihi.square.domain.timesale.dto;

import com.hihi.square.domain.menu.dto.MenuDto;
import com.hihi.square.domain.timesale.entity.TimeSaleMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSaleMenuDto {
    Integer id;
    MenuDto menu;
    Integer salePrice;
    Integer inventory;

    public static TimeSaleMenuDto toRes(TimeSaleMenu timeSaleMenu, MenuDto menu){
        return TimeSaleMenuDto.builder()
                .id(timeSaleMenu.getId())
                .menu(menu)
                .salePrice(timeSaleMenu.getSalePrice())
                .inventory(timeSaleMenu.getInventory())
                .build();
    }
}
