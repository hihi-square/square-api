package com.hihi.square.domain.partnership.dto.request;

import com.hihi.square.domain.partnership.entity.PartnershipAcceptState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePartnershipAcceptStateReq {
    private Integer id;
    private PartnershipAcceptState state;
}
