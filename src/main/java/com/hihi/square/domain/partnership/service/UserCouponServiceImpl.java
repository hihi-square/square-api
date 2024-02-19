package com.hihi.square.domain.partnership.service;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.buyer.repository.BuyerRepository;
import com.hihi.square.domain.partnership.dto.response.UserCouponRes;
import com.hihi.square.domain.partnership.entity.UserCoupon;
import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.partnership.repository.UserCouponRepository;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.global.error.type.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCouponServiceImpl implements UserCouponService {

    private final BuyerRepository buyerRepository;

    private final UserCouponRepository userCouponRepository;

    // 쿠폰 발급
    @Override
    public void addUserCoupon(List<Partnership> partnershipList, Buyer buyer) {

    }

    @Override
    public void cancelUserCoupon(List<UserCoupon> userCouponList) {

    }

    @Override
    public List<UserCouponRes> availableOrderUserCouponList(Integer price, Buyer buyer, Store store) {
        List<UserCoupon> userCouponList = userCouponRepository.findAllByBuyerAndStoreAndIsUsedAndExpiredTimeBeforeAndCouponAvailableLessThanEqual(buyer, store, false, LocalDateTime.now(), price);
        List<UserCouponRes> resultList = new ArrayList<>();
        for(UserCoupon userCoupon : userCouponList) {
            resultList.add(UserCouponRes.toRes(userCoupon));
        }
        return resultList;
    }

    @Override
    public List<UserCouponRes> availableUserCouponList(Store store, Buyer buyer) {
        List<UserCoupon> userCouponList = userCouponRepository.findAllByBuyerAndStoreAndIsUsedAndExpiredTimeBefore(buyer, store, false, LocalDateTime.now());
        List<UserCouponRes> resultList = new ArrayList<>();
        for(UserCoupon userCoupon : userCouponList) {
            resultList.add(UserCouponRes.toRes(userCoupon));
        }
        return resultList;
    }

    @Override
    public List<UserCouponRes> availableBuyerUserCouponList(Integer buyerId) {
        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(() -> new UserNotFoundException("유효하지 않은 유저 아이디 입니다."));
        List<UserCoupon> userCouponList = userCouponRepository.findAllByBuyerAndIsUsedAndExpiredTimeBefore(buyer, false, LocalDateTime.now());
        List<UserCouponRes> resultList = new ArrayList<>();
        for(UserCoupon userCoupon : userCouponList) {
            resultList.add(UserCouponRes.toRes(userCoupon));
        }
        return resultList;
    }

    @Override
    public List<UserCouponRes> partnershipsUserCouponOverview(Store store) {
        return null;
    }
}
