package com.hihi.square.domain.activity.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddActivityReq {
    @NotNull
    private Long emdId;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
