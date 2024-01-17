package com.hihi.square.domain.activity.dto;

import com.hihi.square.domain.activity.entity.EmdAddress;
import com.hihi.square.domain.activity.entity.SiggAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiggAddressDto {
    private Long id;
    private String name;

    public static SiggAddressDto toRes(SiggAddress siggAddress) {
        return SiggAddressDto.builder()
                .id(siggAddress.getId())
                .name(siggAddress.getName())
                .build();
    }
}
