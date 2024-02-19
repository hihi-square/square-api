//package com.hihi.square.domain.coupon2.repository;
//
//import com.hihi.square.common.CommonStatus;
//import com.hihi.square.domain.coupon2.entity.Coupon;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.Optional;
//
//public interface CouponRepository extends JpaRepository<Coupon, Integer> {
//    @Query("select c from Coupon c where c.id=:cId and c.status=:status")
//    Optional<Coupon> findById(@Param("cId") Integer cId, @Param("status") CommonStatus status);
//}
