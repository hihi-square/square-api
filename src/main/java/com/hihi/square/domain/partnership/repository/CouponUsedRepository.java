package com.hihi.square.domain.partnership.repository;

import com.hihi.square.domain.partnership.entity.CouponUsed;
import com.hihi.square.domain.partnership.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponUsedRepository extends JpaRepository<CouponUsed, Integer> {
    Optional<CouponUsed> findByUserCoupon(UserCoupon userCoupon);
}
