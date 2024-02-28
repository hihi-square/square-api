//package com.hihi.square.domain.coupon2.repository;
//
//import com.hihi.square.common.CommonStatus;
//import com.hihi.square.domain.coupon2.entity.UserCoupon;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//public interface UserCouponRepository extends JpaRepository<UserCoupon, Integer> {
//    @Query("select uc from UserCoupon uc where uc.id=:ucId and uc.user.usrId=:usrId and uc.status=:status")
//    Optional<UserCoupon> findById(@Param("ucId") Integer ucId, @Param("usrId") Integer usrId, @Param("status") CommonStatus status);
//
//    @Transactional
//    @Modifying
//    @Query("update UserCoupon uc set uc.status=:status, uc.usedAt=:usedAt where uc.id=:ucId")
//    void updateStatus(@Param("ucId") Integer ucId, @Param("status") CommonStatus status, @Param("usedAt") LocalDateTime usedAt);
//}
