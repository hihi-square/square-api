package com.hihi.square.domain.partnership.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.partnership.dto.response.UserCouponRes;
import com.hihi.square.domain.partnership.service.UserCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
@Slf4j
public class CouponController {

    private final UserCouponService userCouponService;

    @GetMapping
    public ResponseEntity getMyCoupons(Authentication authentication) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        List<UserCouponRes> res = userCouponService.availableBuyerUserCouponList(buyerId);
        return new ResponseEntity(CommonRes.success(res), HttpStatus.OK);
    }
}
