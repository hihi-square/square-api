package com.hihi.square.domain.menu.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.dto.MenuReq;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="menu")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "men_id")
    Integer id;
    String name;
    Integer price;
    @Enumerated(EnumType.STRING)
    CommonStatus status;
    String description;
    Boolean isPopular;
    Boolean isRepresentative;
    Integer sequence;
    String image;
    String thumbnail;

    @ManyToOne
    @JoinColumn(name = "sto_id")
    Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mec_id", nullable = true)
    MenuCategory menuCategory;

    public static Menu toEntity(MenuReq menuReq, MenuCategory menuCategory, Store store){
        return Menu.builder()
                .id(menuReq.getId())
                .name(menuReq.getName())
                .price(menuReq.getPrice() == null ? 0 : menuReq.getPrice())
                .status(menuReq.getStatus() == null ? CommonStatus.ACTIVE : menuReq.getStatus())
                .description(menuReq.getDescription())
                .isPopular(menuReq.getIsPopular() != null)
                .isRepresentative(menuReq.getIsRepresentative() != null)
                .sequence(menuReq.getSequence())
                .image(menuReq.getImage())
                .thumbnail(menuReq.getThumbnail())
                .store(store)
                .menuCategory(menuCategory)
                .build();
    }
}
