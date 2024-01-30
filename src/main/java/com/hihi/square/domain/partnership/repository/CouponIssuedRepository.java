package com.hihi.square.domain.partnership.repository;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.partnership.entity.CouponIssued;
import com.hihi.square.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponIssuedRepository extends JpaRepository<CouponIssued, Integer> {
    @Query("select ci from CouponIssued ci join fetch ci.buyer join fetch ci.partnership join fetch ci.store where ci.buyer = :buyer and ci.store = :store and ci.isUsed = :isUsed and :expiredTime < ci.expiredTime and :couponAvailable <= ci.couponAvailable")
    List<CouponIssued> findAllByBuyerAndStoreAndIsUsedAndExpiredTimeBeforeAndCouponAvailableLessThanEqual(Buyer buyer, Store store, boolean isUsed, LocalDateTime expiredTime, Integer couponAvailable);

    @Query("select ci from CouponIssued ci join fetch ci.buyer join fetch ci.partnership join fetch ci.store where ci.buyer = :buyer and ci.store = :store and ci.isUsed = :isUsed and :expiredTime < ci.expiredTime")
    List<CouponIssued> findAllByBuyerAndStoreAndIsUsedAndExpiredTimeBefore(Buyer buyer, Store store, boolean isUsed, LocalDateTime expiredTime);

    @Query("select ci from CouponIssued ci where ci.buyer = :buyer and ci.isUsed = :isUsed and :expiredTime < ci.expiredTime")
    List<CouponIssued> findAllByBuyerAndIsUsedAndExpiredTimeBefore(Buyer buyer, boolean isUsed, LocalDateTime expiredTime);
}
