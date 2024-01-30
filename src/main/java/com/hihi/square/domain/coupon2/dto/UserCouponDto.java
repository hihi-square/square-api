package com.hihi.square.domain.coupon2.dto;

import com.hihi.square.domain.coupon2.entity.UserCoupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCouponDto {
    Integer id;
    CouponDto coupon;
    LocalDateTime usedAt;

    public static UserCouponDto toRes(UserCoupon userCoupon, CouponDto coupon){
        return UserCouponDto.builder()
                .id(userCoupon.getId())
                .usedAt(userCoupon.getUsedAt())
                .coupon(coupon)
                .build();
    }
}
