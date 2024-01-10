package com.hihi.square.domain.menucategory.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menucategory.dto.MenuCategoryDto;
import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "menu_category")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class MenuCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    Integer sequence;
    @Enumerated(EnumType.STRING)
    CommonStatus status;

    @ManyToOne
    @JoinColumn(name = "sto_id")
    Store store;

    public static MenuCategory toEntity(MenuCategoryDto menuCategoryReq, Store store){
        return MenuCategory.builder()
                .id(menuCategoryReq.getId())
                .name(menuCategoryReq.getName())
                .sequence(menuCategoryReq.getSequence())
                .store(store)
                .status(menuCategoryReq.getStatus())
                .build();
    }
}
