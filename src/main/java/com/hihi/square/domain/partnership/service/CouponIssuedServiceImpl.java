package com.hihi.square.domain.partnership.service;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.partnership.dto.response.CouponIssuedRes;
import com.hihi.square.domain.partnership.entity.CouponIssued;
import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.partnership.repository.CouponIssuedRepository;
import com.hihi.square.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponIssuedServiceImpl implements CouponIssuedService {

    private final CouponIssuedRepository couponIssuedRepository;

    // 쿠폰 발급
    @Override
    public void addCouponIssued(List<Partnership> partnershipList, Buyer buyer) {

    }

    @Override
    public void cancelCouponIssued(List<CouponIssued> couponIssuedList) {

    }

    @Override
    public List<CouponIssuedRes> availableOrderCouponIssuedList(Integer price, Buyer buyer, Store store) {
        List<CouponIssued> couponIssuedList = couponIssuedRepository.findAllByBuyerAndStoreAndIsUsedAndExpiredTimeBeforeAndCouponAvailableLessThanEqual(buyer, store, false, LocalDateTime.now(), price);
        List<CouponIssuedRes> resultList = new ArrayList<>();
        for(CouponIssued couponIssued : couponIssuedList) {
            resultList.add(CouponIssuedRes.toRes(couponIssued));
        }
        return resultList;
    }

    @Override
    public List<CouponIssuedRes> availableCouponIssuedList(Store store, Buyer buyer) {
        List<CouponIssued> couponIssuedList = couponIssuedRepository.findAllByBuyerAndStoreAndIsUsedAndExpiredTimeBefore(buyer, store, false, LocalDateTime.now());
        List<CouponIssuedRes> resultList = new ArrayList<>();
        for(CouponIssued couponIssued : couponIssuedList) {
            resultList.add(CouponIssuedRes.toRes(couponIssued));
        }
        return resultList;
    }

    @Override
    public List<CouponIssuedRes> availableBuyerCouponIssuedList(Buyer buyer) {
        List<CouponIssued> couponIssuedList = couponIssuedRepository.findAllByBuyerAndIsUsedAndExpiredTimeBefore(buyer, false, LocalDateTime.now());
        List<CouponIssuedRes> resultList = new ArrayList<>();
        for(CouponIssued couponIssued : couponIssuedList) {
            resultList.add(CouponIssuedRes.toRes(couponIssued));
        }
        return resultList;
    }

    @Override
    public List<CouponIssuedRes> partnershipsCouponIssuedOverview(Store store) {
        return null;
    }
}
