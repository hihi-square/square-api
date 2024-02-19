package com.hihi.square.domain.partnership.service;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.partnership.entity.UserCoupon;

import java.util.List;

public interface CouponUsedService {

    // 쿠폰 사용
    void addCouponUsed(UserCoupon userCoupon, Buyer buyer);
    // 쿠폰 사용 취소
    void cancelCouponUsed(List<UserCoupon> userCouponList);
}
