package com.hihi.square.domain.activity.dto;

import com.hihi.square.domain.activity.entity.Activity;
import com.hihi.square.domain.activity.entity.EmdAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmdAddressDto {
    private Long id;
    private String name;
    private SiggAddressDto sigg;

    public static EmdAddressDto toRes(EmdAddress emdAddress) {
        return EmdAddressDto.builder()
                .id(emdAddress.getId())
                .name(emdAddress.getName())
                .sigg(SiggAddressDto.toRes(emdAddress.getSiggAddress()))
                .build();
    }

}
