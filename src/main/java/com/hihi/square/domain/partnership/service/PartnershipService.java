package com.hihi.square.domain.partnership.service;

import com.hihi.square.domain.partnership.dto.request.PartnershipReq;
import com.hihi.square.domain.partnership.dto.request.UpdatePartnershipAcceptStateReq;
import com.hihi.square.domain.partnership.dto.response.PartnershipRes;

import java.util.List;

public interface PartnershipService {
    void addPartnership(Integer stoId, PartnershipReq req);

    void updatePartnership(Integer stoId, PartnershipReq req);

    void updatePartnershipAcceptState(Integer stoId, UpdatePartnershipAcceptStateReq req);

    List<PartnershipRes> getPartnerships(Integer stoId);
}
