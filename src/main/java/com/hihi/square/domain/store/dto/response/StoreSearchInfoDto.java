package com.hihi.square.domain.store.dto.response;

import com.hihi.square.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSearchInfoDto {
    private Integer usrId;
    private String name;
    private String menuString;
    private boolean isTs;
    private boolean isPn;
    private boolean isDibs;
    private String profileImage;
    private double latitude;
    private double longitude;

    public static StoreSearchInfoDto toRes(Store store, boolean isTs, boolean isPn, boolean isDibs, String menuString) {
        return StoreSearchInfoDto.builder()
                .usrId(store.getUsrId())
                .name(store.getStoreName())
                .menuString(menuString)
                .isTs(isTs)
                .isPn(isPn)
                .isDibs(false)
                .profileImage(store.getProfileImage())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .build();
    }

    public void updateMenuString(String menuString) {
        this.menuString = menuString;
    }
}
