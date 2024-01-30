package com.hihi.square.domain.activity.dto.response;

import com.hihi.square.domain.activity.entity.EmdAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmdAddressRes {
    private Long id;
    private String name;
    private SiggAddressRes sigg;

    public static EmdAddressRes toRes(EmdAddress emdAddress) {
        return EmdAddressRes.builder()
                .id(emdAddress.getId())
                .name(emdAddress.getName())
                .sigg(SiggAddressRes.toRes(emdAddress.getSiggAddress()))
                .build();
    }

}
