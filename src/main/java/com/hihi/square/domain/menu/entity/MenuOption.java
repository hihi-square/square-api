package com.hihi.square.domain.menu.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.dto.MenuOptionDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "menu_option")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class MenuOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    Integer price;
    Integer quantity;
    Integer sequence;
    @Enumerated(EnumType.STRING)
    CommonStatus status;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    Menu menu;

    //상태 추가 여부??

    public static MenuOption toEntity(MenuOptionDto menuOptionDto, Menu menu){
        return MenuOption.builder()
                .id(menuOptionDto.getId())
                .name(menuOptionDto.getName())
                .price(menuOptionDto.getPrice())
                .quantity(menuOptionDto.getQuantity())
                .sequence(menuOptionDto.getSequence())
                .menu(menu)
                .build();
    }
}
