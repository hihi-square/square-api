package com.hihi.square.domain.activity.dto;

import com.hihi.square.domain.activity.entity.Activity;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.activity.entity.EmdAddress;
import jakarta.persistence.*;
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
public class ActivityDto {
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "emd_id")
    private EmdAddressDto emdAddress;

    private Double latitude;
    private Double longitude;

    @Min(0) @Max(2)
    private Integer depth; // 해당 지역 기준으로 확장되는 칸 수

    @ManyToOne
    @JoinColumn(name = "usr_id")
    private Buyer buyer;

    public static ActivityDto toRes(Activity activity) {
        return ActivityDto.builder()
                .id(activity.getId())
                .emdAddress(EmdAddressDto.toRes(activity.getEmdAddress()))
                .latitude(activity.getLatitude())
                .longitude(activity.getLongitude())
                .depth(activity.getDepth())
                .build();
    }
}
