package com.hihi.square.domain.menu.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.dto.StoreMenuDto;
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

    @ManyToOne
    @JoinColumn(name = "mec_id", nullable = true)
    MenuCategory menuCategory;

    public static Menu toEntity(StoreMenuDto storeMenuDto, MenuCategory menuCategory, Store store){
        return Menu.builder()
                .id(storeMenuDto.getId())
                .name(storeMenuDto.getName())
                .price(storeMenuDto.getPrice() == null ? 0 : storeMenuDto.getPrice())
                .status(storeMenuDto.getStatus() == null ? CommonStatus.ACTIVE : storeMenuDto.getStatus())
                .description(storeMenuDto.getDescription())
                .isPopular(storeMenuDto.getIsPopular() != null)
                .isRepresentative(storeMenuDto.getIsRepresentative() != null)
                .sequence(storeMenuDto.getSequence())
                .image(storeMenuDto.getImage())
                .thumbnail(storeMenuDto.getThumbnail())
                .store(store)
                .menuCategory(menuCategory)
                .build();
    }
}
