package com.hihi.square.domain.partnership.dto.response;

import com.hihi.square.domain.menu.dto.MenuInfoRes;
import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.store.dto.response.StoreInfoRes;
import com.hihi.square.domain.store.dto.response.StoreRes;
import com.hihi.square.domain.store.dto.response.StoreShortInfoRes;
import com.hihi.square.domain.store.entity.Store;
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
public class PartnershipRes {
    private Integer id;

    private StoreShortInfoRes issStore; // 쿠폰 발급(issue) 가게

    private StoreShortInfoRes useStore; // 쿠폰 사용(use) 가게

    private MenuInfoRes menu; // 어떤 상품을 사면 쿠폰을 발급해주는지

    private String acceptState;
    @Min(0)
    private Integer couponAvailable; // 쿠폰 사용기준 금액 x원 이상 구매시 사용 가능한
    @Min(0)
    private Integer couponSale; // 쿠폰 금액 y원 할인 쿠폰

    private LocalDateTime startTime; // 발급 시작일

    private LocalDateTime finishTime; // 발급 종료일
    @Min(0)
    private Long availableTime; // 쿠폰 사용가능 시간. 분 기준
    @Min(0)
    private Integer issChargeAmount; //쿠폰 발급 가게가 부담하는 가격
    @Min(0)
    private Integer useChargeAmount; // 쿠폰 사용 가게가 부담하는 가격

    private LocalDateTime createdAt; // 쿠폰 생성 시간

    public static PartnershipRes toRes(Partnership partnership, Store issStore, Store useStore, MenuInfoRes menu) {
        return PartnershipRes.builder()
                .id(partnership.getId())
                .issStore(StoreShortInfoRes.toRes(issStore))
                .useStore(StoreShortInfoRes.toRes(useStore))
                .menu(menu)
                .acceptState(partnership.getAcceptState().toString())
                .couponAvailable(partnership.getCouponAvailable())
                .couponSale(partnership.getCouponSale())
                .startTime(partnership.getStartTime())
                .finishTime(partnership.getFinishTime())
                .availableTime(partnership.getAvailableTime())
                .issChargeAmount(partnership.getIssChargeAmount())
                .useChargeAmount(partnership.getUseChargeAmount())
                .createdAt(partnership.getCreatedAt())
                .build();
    }
}
