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
    Integer salePrice;
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
                .salePrice(timeSaleMenuDto.getSalePrice())
                .inventory(timeSaleMenuDto.getInventory())
                .timeSale(timeSale)
                .menu(menu)
                .build();
    }
}
