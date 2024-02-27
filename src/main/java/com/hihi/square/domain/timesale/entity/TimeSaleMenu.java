package com.hihi.square.domain.timesale.entity;

import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.timesale.dto.TimeSaleMenuDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "sale_menu")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class TimeSaleMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    TimeSaleType type;
    Integer discount;
    Integer inventory;

    @OneToOne
    @JoinColumn(name = "sal_id")
    TimeSale timeSale;
    @ManyToOne
    @JoinColumn(name = "men_id")
    Menu menu;

    public static TimeSaleMenu toEntity(TimeSaleMenuDto timeSaleMenuDto, TimeSale timeSale, Menu menu){
        return TimeSaleMenu.builder()
                .id(timeSaleMenuDto.getId())
                .type(timeSaleMenuDto.getType())
                .discount(timeSaleMenuDto.getDiscount())
                .inventory(timeSaleMenuDto.getInventory())
                .timeSale(timeSale)
                .menu(menu)
                .build();
    }
}
