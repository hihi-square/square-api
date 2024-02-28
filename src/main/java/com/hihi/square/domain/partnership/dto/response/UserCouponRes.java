package com.hihi.square.domain.partnership.dto.response;

import com.hihi.square.domain.partnership.entity.UserCoupon;
import com.hihi.square.domain.store.dto.response.StoreInfoRes;
import com.hihi.square.domain.store.dto.response.StoreShortInfoRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponRes {
    private Integer id;
    private Integer partnershipId; // 쿠폰 발급한 제휴 ID
    private Integer ordId; // 어떤 주문을 통해 쿠폰이 발급되었는지
    private Integer buyerId; // 쿠폰 사용 가능 유저
    private boolean isUsed; // 사용 완료 했는지
    private LocalDateTime expiredTime; // 사용 종료일
    private StoreShortInfoRes store; // 어떤 가게에서 사용할 수 있는지
    private Integer couponAvailable; // 쿠폰 사용기준 금액 x원 이상 구매시 사용 가능한
    private Integer couponSale; // 쿠폰 금액 y원 할인 쿠폰

    public static UserCouponRes toRes(UserCoupon userCoupon) {
        return UserCouponRes.builder()
                .id(userCoupon.getId())
                .partnershipId(userCoupon.getPartnership().getId())
                .ordId(userCoupon.getOrders().getId())
                .buyerId(userCoupon.getBuyer().getUsrId())
                .isUsed(userCoupon.isUsed())
                .expiredTime(userCoupon.getExpiredTime())
                .store(StoreShortInfoRes.toRes(userCoupon.getStore()))
                .couponAvailable(userCoupon.getCouponAvailable())
                .couponSale(userCoupon.getCouponSale())
                .build();
    }
}
