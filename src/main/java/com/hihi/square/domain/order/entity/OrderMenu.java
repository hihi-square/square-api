package com.hihi.square.domain.order.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.order.dto.OrderMenuDto;
import com.hihi.square.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "order_menu")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class OrderMenu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer quantity;
    Integer price;

    @ManyToOne
    @JoinColumn
    Orders order;
    @ManyToOne
    @JoinColumn
    Menu menu;
    @ManyToOne
    @JoinColumn(name = "usr_id")
    User user;

    public static OrderMenu toEntity(OrderMenuDto orderMenuDto, Orders order, Menu menu, User user){
        return OrderMenu.builder()
                .id(orderMenuDto.getId())
                .quantity(orderMenuDto.getQuantity())
                .price(orderMenuDto.getPrice())
                .order(order)
                .menu(menu)
                .user(user)
                .build();
    }
}
