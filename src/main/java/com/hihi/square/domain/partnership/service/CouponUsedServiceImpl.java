package com.hihi.square.domain.partnership.service;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.partnership.entity.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponUsedServiceImpl implements CouponUsedService {
    @Override
    public void addCouponUsed(UserCoupon userCoupon, Buyer buyer) {

    }

    @Override
    public void cancelCouponUsed(List<UserCoupon> userCouponList) {

    }
}
