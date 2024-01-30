package com.hihi.square.domain.activity.dto.response;

import com.hihi.square.domain.activity.entity.Activity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRes {
    private Integer id;

    private EmdAddressRes emdAddress;

    private Double latitude;
    private Double longitude;

    @Min(0) @Max(2)
    private Integer depth; // 해당 지역 기준으로 확장되는 칸 수

    private Integer buyerId;

    private Boolean isMain;

    public static ActivityRes toRes(Activity activity) {
        return ActivityRes.builder()
                .id(activity.getId())
                .emdAddress(EmdAddressRes.toRes(activity.getEmdAddress()))
                .latitude(activity.getLatitude())
                .longitude(activity.getLongitude())
                .depth(activity.getDepth())
                .buyerId(activity.getBuyer().getUsrId())
                .isMain(activity.isMain())
                .build();
    }
}
