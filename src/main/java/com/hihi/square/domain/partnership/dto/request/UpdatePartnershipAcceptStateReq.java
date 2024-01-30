package com.hihi.square.domain.partnership.dto.request;

import com.hihi.square.domain.partnership.entity.PartnershipAcceptState;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePartnershipAcceptStateReq {
    @NotNull
    private Integer id;
    @NotNull
    private PartnershipAcceptState state;
}
