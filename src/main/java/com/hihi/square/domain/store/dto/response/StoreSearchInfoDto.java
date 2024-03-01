package com.hihi.square.domain.store.dto.response;

import com.hihi.square.domain.store.entity.Store;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StoreSearchInfoDto {
    private Integer usrId;
    private String name;
    private String menuString;
    private Integer category;
    private Boolean isTs;
    private Boolean isPn;
    private Boolean isDibs;
    private String profileImage;
    private Double latitude;
    private Double longitude;

    public StoreSearchInfoDto(Integer usrId, String name, Integer category, Boolean isTs, Boolean isPn, Boolean isDibs, String profileImage, Double latitude, Double longitude) {
        this.usrId = usrId;
        this.name = name;
        this.menuString = menuString;
        this.category = category;
        this.isTs = isTs;
        this.isPn = isPn;
        this.isDibs = isDibs;
        this.profileImage = profileImage;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public static StoreSearchInfoDto toRes(Store store, boolean isTs, boolean isPn, boolean isDibs, String menuString) {
        return StoreSearchInfoDto.builder()
                .usrId(store.getUsrId())
                .name(store.getStoreName())
                .menuString(menuString)
                .category(store.getCategory().getId())
                .isTs(isTs)
                .isPn(isPn)
                .isDibs(isDibs)
                .profileImage(store.getProfileImage())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .build();
    }

    public void updateMenuString(String menuString) {
        this.menuString = menuString;
    }
}
