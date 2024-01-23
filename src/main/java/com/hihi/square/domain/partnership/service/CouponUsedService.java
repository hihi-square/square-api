package com.hihi.square.domain.partnership.service;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.partnership.entity.CouponIssued;

import java.util.List;

public interface CouponUsedService {

    // 쿠폰 사용
    void addCouponUsed(CouponIssued couponIssued, Buyer buyer);
    // 쿠폰 사용 취소
    void cancelCouponUsed(List<CouponIssued> couponIssuedList);
}
