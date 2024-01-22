package com.hihi.square.domain.partnership.service;

import com.hihi.square.domain.partnership.dto.request.PartnershipDto;
import com.hihi.square.domain.partnership.dto.request.UpdatePartnershipAcceptStateReqDto;
import org.hibernate.sql.Update;

public interface PartnershipService {
    void addPartnership(Integer stoId, PartnershipDto req);

    void updatePartnership(Integer stoId, PartnershipDto req);

    void updatePartnershipAcceptState(Integer stoId, UpdatePartnershipAcceptStateReqDto req);
}
