package com.hihi.square.domain.partnership.repository;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.partnership.entity.CouponIssued;
import com.hihi.square.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponIssuedRepository extends JpaRepository<CouponIssued, Integer> {
    List<CouponIssued> findAllByBuyerAndStoreAndIsUsedAndExpiredTimeBeforeAndCouponAvailableLessThanEqual(Buyer buyer, Store store, boolean isUsed, LocalDateTime expiredTime, Integer couponAvailable);
    List<CouponIssued> findAllByBuyerAndStoreAndIsUsedAndExpiredTimeBefore(Buyer buyer, Store store, boolean isUsed, LocalDateTime expiredTime);
    List<CouponIssued> findAllByBuyerAndIsUsedAndExpiredTimeBefore(Buyer buyer, boolean isUsed, LocalDateTime expiredTime);
}
