package com.hihi.square.domain.order.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.menu.entity.MenuOption;
import com.hihi.square.domain.order.dto.OrderMenuOptionDto;
import com.hihi.square.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="order_menu_option")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class OrderMenuOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    Integer quantity;
    Integer price;

    @ManyToOne
    @JoinColumn(name = "meo_id")
    MenuOption menuOption;
    @ManyToOne
    @JoinColumn(name = "orm_id")
    OrderMenu orderMenu;
    @ManyToOne
    @JoinColumn(name = "usr_id")
    User user;

    public static OrderMenuOption toEntity(OrderMenuOptionDto omod, MenuOption menuOption, OrderMenu orderMenu, User user){
        return OrderMenuOption.builder()
                .id(omod.getId())
                .name(omod.getName())
                .quantity(omod.getQuantity())
                .price(omod.getPrice())
                .menuOption(menuOption)
                .orderMenu(orderMenu)
                .user(user)
                .build();
    }
}
