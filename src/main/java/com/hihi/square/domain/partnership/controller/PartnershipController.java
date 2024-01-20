package com.hihi.square.domain.partnership.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.partnership.dto.request.PartnershipDto;
import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.partnership.service.PartnershipService;
import jakarta.servlet.http.Part;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partnerships")
@RequiredArgsConstructor
@Slf4j
public class PartnershipController {

    private final PartnershipService partnershipService;

    @PostMapping
    public ResponseEntity addPartnership(Authentication authentication, @RequestBody @Valid PartnershipDto req) {
        Integer stoId = Integer.parseInt(authentication.getName());
        partnershipService.addPartnership(stoId, req);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity updatePartnership(Authentication authentication, @RequestBody @Valid PartnershipDto req) {
        Integer stoId = Integer.parseInt(authentication.getName());
        partnershipService.updatePartnership(stoId, req);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.OK);
    }
}
