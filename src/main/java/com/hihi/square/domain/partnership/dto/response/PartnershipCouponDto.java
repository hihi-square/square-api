package com.hihi.square.domain.partnership.dto.response;

import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.store.dto.response.StoreShortInfoRes;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnershipCouponDto {
    Integer id;
    LocalDateTime startTime;
    LocalDateTime finishTime;
    Integer couponAvailable; // 쿠폰 사용기준 금액 x원 이상 구매시 사용 가능한
    @Min(0)
    Integer couponSale; // 쿠폰 금액 y원 할인 쿠폰
    StoreShortInfoRes useStore; // 쿠폰 사용(use) 가게

    public static PartnershipCouponDto toRes(Partnership partnership){
        return PartnershipCouponDto.builder()
                .id(partnership.getId())
                .startTime(partnership.getStartTime())
                .finishTime(partnership.getFinishTime())
                .couponAvailable(partnership.getCouponAvailable())
                .couponSale(partnership.getCouponSale())
                .useStore(StoreShortInfoRes.toRes(partnership.getUseStore()))
                .build();
    }
}
