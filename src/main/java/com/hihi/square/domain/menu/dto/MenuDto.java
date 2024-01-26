package com.hihi.square.domain.menu.dto;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.entity.Menu;
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
public class MenuDto {
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
    List<MenuOptionDto> options;

    public static MenuDto toRes(Menu menu, Integer mcId, List<MenuOptionDto> optionList){
        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .status(menu.getStatus())
                .description(menu.getDescription())
                .isPopular(menu.getIsPopular())
                .isRepresentative(menu.getIsRepresentative())
                .image(menu.getImage())
                .thumbnail(menu.getThumbnail())
                .mcId(mcId)
                .options(optionList)
                .build();
    }
}
