package com.hihi.square.domain.partnership.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.partnership.dto.request.AddPartnershipReq;
import com.hihi.square.domain.partnership.dto.request.UpdatePartnershipAcceptStateReq;
import com.hihi.square.domain.partnership.dto.request.UpdatePartnershipReq;
import com.hihi.square.domain.partnership.dto.response.PartnershipRes;
import com.hihi.square.domain.partnership.service.PartnershipService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partnerships")
@RequiredArgsConstructor
@Slf4j
public class PartnershipController {

    private final PartnershipService partnershipService;

    // 제휴 추가
    @PostMapping
    public ResponseEntity<?> addPartnership(Authentication authentication, @RequestBody @Valid AddPartnershipReq req) {
        Integer stoId = Integer.parseInt(authentication.getName());
        partnershipService.addPartnership(stoId, req);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.CREATED);
    }

    // 제휴 수정
    @PatchMapping
    public ResponseEntity<?> updatePartnership(Authentication authentication, @RequestBody @Valid UpdatePartnershipReq req) {
        Integer stoId = Integer.parseInt(authentication.getName());
        partnershipService.updatePartnership(stoId, req);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.OK);
    }

    // 제휴 상태 변경
    @PostMapping("/accept-state")
    public ResponseEntity<?> updatePartnershipAcceptState(Authentication authentication, @RequestBody @Valid UpdatePartnershipAcceptStateReq req) {
        Integer stoId = Integer.parseInt(authentication.getName());
        partnershipService.updatePartnershipAcceptState(stoId, req);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.OK);
    }

    // 내 제휴 리스트 가져오기
    @GetMapping
    public ResponseEntity<List<PartnershipRes>> getUserPartnerships(Authentication authentication) {
        Integer stoId = Integer.parseInt(authentication.getName());
        List<PartnershipRes> list = partnershipService.getPartnerships(stoId);
        return new ResponseEntity(CommonRes.success(list), HttpStatus.OK);
    }

    // 특정 가게와 진행했던 제휴 리스트 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<List<PartnershipRes>> getUserPartnershipsWithOtherStore(Authentication authentication, @PathVariable @NonNull Integer id) {
        Integer stoId = Integer.parseInt(authentication.getName());
        List<PartnershipRes> list = partnershipService.getPartnerships(stoId, id);
        return new ResponseEntity(CommonRes.success(list), HttpStatus.OK);
    }

}
