package com.hihi.square.domain.timesale.dto;

import com.hihi.square.domain.menu.dto.StoreMenuDto;
import com.hihi.square.domain.timesale.entity.TimeSaleMenu;
import com.hihi.square.domain.timesale.entity.TimeSaleType;
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
    StoreMenuDto menu;
    TimeSaleType type;
    Integer discount;
    Integer inventory;

    public static TimeSaleMenuDto toRes(TimeSaleMenu timeSaleMenu, StoreMenuDto menu){
        return TimeSaleMenuDto.builder()
                .id(timeSaleMenu.getId())
                .menu(menu)
                .type(timeSaleMenu.getType())
                .discount(timeSaleMenu.getDiscount())
                .inventory(timeSaleMenu.getInventory())
                .build();
    }
}
