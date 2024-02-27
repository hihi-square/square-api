package com.hihi.square.domain.partnership.service;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.order.entity.Orders;
import com.hihi.square.domain.partnership.entity.CouponUsed;
import com.hihi.square.domain.partnership.entity.UserCoupon;
import com.hihi.square.domain.partnership.repository.CouponUsedRepository;
import com.hihi.square.domain.partnership.repository.UserCouponRepository;
import com.hihi.square.domain.user.entity.User;
import com.hihi.square.global.error.type.UserMismachException;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponUsedServiceImpl implements CouponUsedService {

    private final UserCouponRepository userCouponRepository;
    private final CouponUsedRepository couponUsedRepository;

    @Override
    public void addCouponUsed(UserCoupon userCoupon, Buyer buyer, Orders order) {
        if (buyer.getUsrId() != userCoupon.getBuyer().getUsrId()) throw new UserMismachException("쿠폰 소유자와 주문자가 일치하지 않습니다.");
        userCoupon.updateUseCoupon(true);
        couponUsedRepository.save(CouponUsed
                .builder()
                .userCoupon(userCoupon)
                .orders(order)
                .buyer(buyer)
                .status(CommonStatus.ACTIVE)
                .build());
    }

    @Override
    public void cancelCouponUsed(Orders order) {
        Optional<UserCoupon> optionalUserCoupon = userCouponRepository.findByOrders(order);
        if (optionalUserCoupon.isEmpty()) return;
        UserCoupon userCoupon = optionalUserCoupon.get();
        userCoupon.updateUseCoupon(false);
        Optional<CouponUsed> couponUsed = couponUsedRepository.findByUserCoupon(userCoupon);
        couponUsed.get().cancelUse();
    }
}
