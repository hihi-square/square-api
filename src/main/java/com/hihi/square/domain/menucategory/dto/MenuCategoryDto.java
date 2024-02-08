package com.hihi.square.domain.menucategory.dto;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuCategoryDto {
    Integer id;
    @NotNull(message = "카테고리 이름은 필수 입력값입니다.")
    String name;
    Integer sequence;    //null이면 맨 마지막
    CommonStatus status;
    @NotNull(message = "가게 아이디는 필수 입력값입니다.")
    Integer stoId;

    public static MenuCategoryDto toRes(MenuCategory menuCategory){
        return MenuCategoryDto.builder()
                .id(menuCategory.getId())
                .name(menuCategory.getName())
                .sequence(menuCategory.getSequence())
                .stoId(menuCategory.getStore() == null ? null : menuCategory.getStore().getUsrId())
                .build();
    }
}
