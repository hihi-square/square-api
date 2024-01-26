package com.hihi.square.domain.menu.dto;

import com.hihi.square.domain.menu.entity.MenuOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuOptionDto {
    Integer id;
    String name;
    Integer price;
    Integer quantity;
    Integer sequence;

    public static MenuOptionDto toRes(MenuOption menuOption){
        return MenuOptionDto.builder()
                .id(menuOption.getId())
                .name(menuOption.getName())
                .price(menuOption.getPrice())
                .quantity(menuOption.getQuantity())
                .sequence(menuOption.getSequence())
                .build();
    }
}
