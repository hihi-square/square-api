package com.hihi.square.domain.order.dto;

import com.hihi.square.domain.menu.entity.MenuOption;
import com.hihi.square.domain.order.entity.OrderMenuOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMenuOptionDto {
    Integer id;
    Integer quantity;
    Integer totalPrice;
    Integer optionId;
    String name;
    Integer price;

    public static OrderMenuOptionDto toRes(OrderMenuOption omo, MenuOption mo){
        return OrderMenuOptionDto.builder()
                .id(omo.getId())
                .quantity(omo.getQuantity())
                .name(mo != null ? mo.getName() : null)
                .price(mo != null ? mo.getPrice() : null)
                .build();
    }
}
