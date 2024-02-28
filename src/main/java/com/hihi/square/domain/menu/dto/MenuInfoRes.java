package com.hihi.square.domain.menu.dto;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import com.hihi.square.domain.store.dto.response.StoreInfoRes;
import com.hihi.square.domain.store.dto.response.StoreShortInfoRes;
import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfoRes {
    Integer id;
    String name;
    Integer price;
    CommonStatus status;
    String image;
    String thumbnail;
    StoreShortInfoRes store;

    public static MenuInfoRes toRes(Menu menu) {
        return MenuInfoRes.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .status(menu.getStatus())
                .image(menu.getImage())
                .thumbnail(menu.getThumbnail())
                .store(StoreShortInfoRes.toRes(menu.getStore()))
                .build();
    }

}
