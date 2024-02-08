package com.hihi.square.domain.menu.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.dto.MenuDto;
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

    public static Menu toEntity(MenuDto menuDto, MenuCategory menuCategory, Store store){
        return Menu.builder()
                .id(menuDto.getId())
                .name(menuDto.getName())
                .price(menuDto.getPrice() == null ? 0 : menuDto.getPrice())
                .status(menuDto.getStatus() == null ? CommonStatus.ACTIVE : menuDto.getStatus())
                .description(menuDto.getDescription())
                .isPopular(menuDto.getIsPopular() != null)
                .isRepresentative(menuDto.getIsRepresentative() != null)
                .sequence(menuDto.getSequence())
                .image(menuDto.getImage())
                .thumbnail(menuDto.getThumbnail())
                .store(store)
                .menuCategory(menuCategory)
                .build();
    }
}
