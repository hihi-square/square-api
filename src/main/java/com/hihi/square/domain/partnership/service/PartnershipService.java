package com.hihi.square.domain.partnership.service;

import com.hihi.square.domain.partnership.dto.request.AddPartnershipReq;
import com.hihi.square.domain.partnership.dto.request.UpdatePartnershipAcceptStateReq;
import com.hihi.square.domain.partnership.dto.request.UpdatePartnershipReq;
import com.hihi.square.domain.partnership.dto.response.PartnershipRes;

import java.util.List;

public interface PartnershipService {
    // 제휴 추가
    void addPartnership(Integer stoId, AddPartnershipReq req);

    // 제휴 수정
    void updatePartnership(Integer stoId, UpdatePartnershipReq req);

    // 제휴 상태 변경
    void updatePartnershipAcceptState(Integer stoId, UpdatePartnershipAcceptStateReq req);

    // a 가게와 관련된 제휴 목록
    List<PartnershipRes> getPartnerships(Integer stoId);

    // a 가게가 현재 진행중인 제휴 목록
    List<PartnershipRes> getAvailablePartnerships(Integer stoId);

    // a 가게와 b 가게의 진행했던 제휴 목록
    List<PartnershipRes> getPartnerships(Integer stoId1, Integer stoId2);

    // a 가게에 대해서 진행중인 제휴 목록
    List<PartnershipRes> getProgressPartnerships(Integer stoId);

    // a 가게에 대해서 진행중인 제휴 목록
    List<PartnershipRes> getProgressPartnershipsByIssStore(Integer stoId);
}
