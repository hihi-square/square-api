package com.hihi.square.domain.order.dto;

import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.order.entity.OrderMenu;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMenuDto {
    Integer id;
    String name;
    Integer price;
    Integer quantity;
    Integer totalPrice;
    Integer menuId;
    List<OrderMenuOptionDto> options;

    public static OrderMenuDto toRes(OrderMenu orderMenu, Menu menu){
        return OrderMenuDto.builder()
                .id(orderMenu.getId())
                .quantity(orderMenu.getQuantity())
                .price(orderMenu.getPrice())
                .menuId(menu != null ? menu.getId() : null)
                .name(menu != null ? menu.getName() : null)
                .price(menu != null ? menu.getPrice() : null)
                .build();
    }
}
