package com.hihi.square.domain.partnership.dto.request;

import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.partnership.entity.PartnershipAcceptState;
import com.hihi.square.domain.store.entity.Store;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePartnershipReq {
    @NotNull
    private Integer id;
    @NotNull
    private Integer issStoreId; // 쿠폰 발급(issue) 가게
    @NotNull
    private Integer useStoreId; // 쿠폰 사용(use) 가게
    @NotNull
    private Integer menuId; // 어떤 상품을 사면 쿠폰을 발급해주는지

    @Min(0) @NotNull
    private Integer couponAvailable; // 쿠폰 사용기준 금액 x원 이상 구매시 사용 가능한
    @Min(0) @NotNull
    private Integer couponSale; // 쿠폰 금액 y원 할인 쿠폰
    @NotNull
    private LocalDateTime startTime; // 발급 시작일
    @NotNull
    private LocalDateTime finishTime; // 발급 종료일
    @Min(0) @NotNull
    private Long availableTime; // 쿠폰 사용가능 시간. 분 기준
    @Min(0) @NotNull
    private Integer issChargeAmount; //쿠폰 발급 가게가 부담하는 가격
    @Min(0) @NotNull
    private Integer useChargeAmount; // 쿠폰 사용 가게가 부담하는 가격

    public static Partnership toEntity(UpdatePartnershipReq req, Store issStore, Store useStore, Store proStore, Menu menu, PartnershipAcceptState state) {
        return Partnership.builder()
                .issStore(issStore)
                .useStore(useStore)
                .proStore(proStore)
                .acceptState(state)
                .menu(menu)
                .couponAvailable(req.getCouponAvailable())
                .couponSale(req.getCouponSale())
                .startTime(req.getStartTime())
                .finishTime(req.getFinishTime())
                .availableTime(req.getAvailableTime())
                .issChargeAmount(req.getIssChargeAmount())
                .useChargeAmount(req.getUseChargeAmount())
                .build();
    }
}
