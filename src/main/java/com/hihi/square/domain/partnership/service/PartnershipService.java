package com.hihi.square.domain.partnership.service;

import com.hihi.square.domain.partnership.dto.request.PartnershipDto;

public interface PartnershipService {
    void addPartnership(Integer stoId, PartnershipDto req);

    void updatePartnership(Integer stoId, PartnershipDto req);
}
