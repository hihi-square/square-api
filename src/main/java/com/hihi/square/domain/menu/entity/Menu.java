package com.hihi.square.domain.menu.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="entity")
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "men_id")
    Integer id;
    String name;
    Integer price;
    CommonStatus status;
    String description;
    Integer sequence;

    @ManyToOne
    @JoinColumn(name = "sto_id")
    Store store;

    @ManyToOne
    @JoinColumn(name = "mec_id")
    MenuCategory menuCategory;
}
