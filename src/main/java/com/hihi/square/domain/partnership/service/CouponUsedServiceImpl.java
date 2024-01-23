package com.hihi.square.domain.partnership.service;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.partnership.entity.CouponIssued;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponUsedServiceImpl implements CouponUsedService {
    @Override
    public void addCouponUsed(CouponIssued couponIssued, Buyer buyer) {

    }

    @Override
    public void cancelCouponUsed(List<CouponIssued> couponIssuedList) {

    }
}
