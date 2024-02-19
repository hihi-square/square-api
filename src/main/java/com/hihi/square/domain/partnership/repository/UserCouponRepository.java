package com.hihi.square.domain.partnership.repository;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.partnership.entity.UserCoupon;
import com.hihi.square.domain.store.entity.Store;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Integer> {
    @Query("select uc from UserCoupon uc join fetch uc.buyer join fetch uc.partnership join fetch uc.store join fetch uc.orders where uc.buyer = :buyer and uc.store = :store and uc.isUsed = :isUsed and :expiredTime < uc.expiredTime and :couponAvailable <= uc.couponAvailable")
    List<UserCoupon> findAllByBuyerAndStoreAndIsUsedAndExpiredTimeBeforeAndCouponAvailableLessThanEqual(Buyer buyer, Store store, boolean isUsed, LocalDateTime expiredTime, Integer couponAvailable);

    @Query("select uc from UserCoupon uc join fetch uc.buyer join fetch uc.partnership join fetch uc.store join fetch uc.orders where uc.buyer = :buyer and uc.store = :store and uc.isUsed = :isUsed and :expiredTime < uc.expiredTime")
    List<UserCoupon> findAllByBuyerAndStoreAndIsUsedAndExpiredTimeBefore(Buyer buyer, Store store, boolean isUsed, LocalDateTime expiredTime);

    @Query("select uc from UserCoupon uc join fetch uc.buyer join fetch uc.partnership join fetch uc.store join fetch uc.orders where uc.buyer = :buyer and uc.isUsed = :isUsed and :expiredTime < uc.expiredTime")
    List<UserCoupon> findAllByBuyerAndIsUsedAndExpiredTimeBefore(Buyer buyer, boolean isUsed, LocalDateTime expiredTime);

    @Query("select uc from UserCoupon uc join fetch uc.buyer join fetch uc.partnership join fetch uc.store join fetch uc.orders where uc.id=:id and uc.buyer = :buyer and :expiredTime < uc.expiredTime and uc.isUsed = :isUsed")
    Optional<UserCoupon> findByIdAndBuyerAndExpiredTimeBeforeAndIsUsed(Integer id, Buyer buyer, LocalDateTime expiredTime, boolean isUsed);

}
