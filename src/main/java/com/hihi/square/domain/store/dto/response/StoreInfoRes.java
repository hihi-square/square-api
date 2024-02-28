package com.hihi.square.domain.store.dto.response;

import com.hihi.square.domain.activity.dto.response.EmdAddressRes;
import com.hihi.square.domain.activity.entity.EmdAddress;
import com.hihi.square.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StoreInfoRes {
    private Integer usrId;
    private String name;
    private String phone;
    private String address;
    private Integer minPickUpTime;
    private Integer maxPickUpTime;
    private Integer duration;
    private boolean isTs;
    private boolean isPn;
    private double latitude;
    private double longitude;
    private String profileImage;


    public static StoreInfoRes toRes(Store store, boolean isTs, boolean isPn) {
        return StoreInfoRes.builder()
                .usrId(store.getUsrId())
                .name(store.getStoreName())
                .phone(store.getPhone())
                .address(store.getAddress().getSiggAddress().getName()+" "+store.getAddress().getName()+" "+store.getDetailAddress())
                .minPickUpTime(store.getMinPickUpTime())
                .maxPickUpTime(store.getMaxPickUpTime())
                .isTs(isTs)
                .isPn(isPn)
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .profileImage(store.getProfileImage())
                .build();
    }

    public static StoreInfoRes toRes(Store store) {
        return StoreInfoRes.builder()
                .usrId(store.getUsrId())
                .name(store.getStoreName())
                .phone(store.getPhone())
                .address(store.getAddress().getSiggAddress().getName()+" "+store.getAddress().getName()+" "+store.getDetailAddress())
                .minPickUpTime(store.getMinPickUpTime())
                .maxPickUpTime(store.getMaxPickUpTime())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .profileImage(store.getProfileImage())
                .build();
    }
}
