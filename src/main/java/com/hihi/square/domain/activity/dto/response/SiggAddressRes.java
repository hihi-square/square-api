package com.hihi.square.domain.activity.dto.response;

import com.hihi.square.domain.activity.entity.SiggAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiggAddressRes {
    private Long id;
    private String name;

    public static SiggAddressRes toRes(SiggAddress siggAddress) {
        return SiggAddressRes.builder()
                .id(siggAddress.getId())
                .name(siggAddress.getName())
                .build();
    }
}
