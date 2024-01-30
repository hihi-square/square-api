package com.hihi.square.domain.activity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddActivityReq {
    private Long emdId;
    private Double latitude;
    private Double longitude;
}
