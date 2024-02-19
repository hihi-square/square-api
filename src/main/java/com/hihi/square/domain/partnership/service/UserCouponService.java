package com.hihi.square.domain.partnership.service;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.partnership.dto.response.UserCouponRes;
import com.hihi.square.domain.partnership.entity.UserCoupon;
import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.store.entity.Store;

import java.util.List;

public interface UserCouponService {
    // 쿠폰 발급
    void addUserCoupon(List<Partnership> partnershipList, Buyer buyer);

    // 쿠폰 발급 취소
    void cancelUserCoupon(List<UserCoupon> userCouponList);

    // 해당 주문에서 사용 가능한 쿠폰 목록
    List<UserCouponRes> availableOrderUserCouponList(Integer price, Buyer buyer, Store store);
    
    // 해당 가게에 대해서 사용 가능한 쿠폰 목록
    List<UserCouponRes> availableUserCouponList(Store store, Buyer buyer);

    // 구매자가 가지고 있는 쿠폰 목록
    List<UserCouponRes> availableBuyerUserCouponList(Integer buyerId);
    
    // a 제휴에 대해서 발급된 쿠폰 현황
    List<UserCouponRes> partnershipsUserCouponOverview(Store store);

}
