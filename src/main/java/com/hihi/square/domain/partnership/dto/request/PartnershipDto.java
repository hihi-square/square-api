package com.hihi.square.domain.partnership.dto.request;

import com.hihi.square.domain.partnership.entity.PartnershipAcceptState;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnershipDto {
    private Integer id;

    private Integer issStoreId; // 쿠폰 발급(issue) 가게

    private Integer useStoreId; // 쿠폰 사용(use) 가게

    private Integer menuId; // 어떤 상품을 사면 쿠폰을 발급해주는지

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
}
