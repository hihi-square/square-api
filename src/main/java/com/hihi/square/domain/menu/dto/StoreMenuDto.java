package com.hihi.square.domain.menu.dto;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreMenuDto {
    Integer id;
    @NotNull(message = "메뉴 이름은 필수 입력값입니다.")
    String name;
    @NotNull(message = "가격은 필수 입력값입니다.")
    Integer price;
    CommonStatus status;
    String description;
    Boolean isPopular;
    Boolean isRepresentative;
    Integer sequence;
    String image;
    String thumbnail;
    @NotNull(message = "가게 아이디는 필수 입력값입니다.")
    Integer stoId;
    Integer mcId;
    String mcName;
    List<MenuOptionDto> options;

    public static StoreMenuDto toRes(Menu menu, MenuCategory mc, List<MenuOptionDto> optionList){
        return StoreMenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .status(menu.getStatus())
                .description(menu.getDescription())
                .isPopular(menu.getIsPopular())
                .isRepresentative(menu.getIsRepresentative())
                .image(menu.getImage())
                .thumbnail(menu.getThumbnail())
                .sequence(menu.getSequence())
                .mcId(mc == null ? null : mc.getId())
                .mcName(mc == null ? null : mc.getName())
                .options(optionList)
                .build();
    }
}
