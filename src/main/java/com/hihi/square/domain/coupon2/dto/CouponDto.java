//package com.hihi.square.domain.coupon2.dto;
//
//import com.hihi.square.common.CommonStatus;
//import com.hihi.square.domain.coupon2.entity.Coupon;
//import com.hihi.square.domain.store.dto.StoreDto;
//import com.hihi.square.domain.store.entity.Store;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class CouponDto {
//    Integer id;
//    String name;
//    LocalDateTime startAt;
//    LocalDateTime expiredAt;
//    Double rate;
//    Integer minPrice;
//    Integer maxPrice;
//    CommonStatus status;
//    StoreDto store;
//
//    public static CouponDto toRes(Coupon coupon, StoreDto store){
//        return CouponDto.builder()
//                .id(coupon.getId())
//                .name(coupon.getName())
//                .startAt(coupon.getStartAt())
//                .expiredAt(coupon.getExpiredAt())
//                .rate(coupon.getRate())
//                .minPrice(coupon.getMinPrice())
//                .maxPrice(coupon.getMaxPrice())
//                .status(coupon.getStatus())
//                .store(store)
//                .build();
//    }
//}
