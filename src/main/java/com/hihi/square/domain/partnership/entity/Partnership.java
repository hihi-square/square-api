package com.hihi.square.domain.partnership.entity;


import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.partnership.dto.request.PartnershipReq;
import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "partnership")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Partnership extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iss_sto_id")
    private Store issStore; // 쿠폰 발급(issue) 가게

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "use_sto_id")
    private Store useStore; // 쿠폰 사용(use) 가게

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_sto_id")
    private Store proStore; // 쿠폰 제안(propose) 가게

    @Enumerated(EnumType.STRING)
    private PartnershipAcceptState acceptState; // 수락 현황

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "men_id")
    private Menu menu; // 어떤 상품을 사면 쿠폰을 발급해주는지

    @Min(0)
    private Integer couponAvailable; // 쿠폰 사용기준 금액 x원 이상 구매시 사용 가능한

    @Min(0)
    private Integer couponSale; // 쿠폰 금액 y원 할인 쿠폰
    private LocalDateTime startTime; // 발급 시작일
    private LocalDateTime finishTime; // 발급 종료일
    private Long availableTime; // 쿠폰 사용가능 시간. 분 기준

    @Min(0)
    private Integer issChargeAmount; //쿠폰 발급 가게가 부담하는 가격

    @Min(0)
    private Integer useChargeAmount; // 쿠폰 사용 가게가 부담하는 가격

    public static Partnership toEntity(PartnershipReq req, Store issStore, Store useStore, Store proStore, Menu menu, PartnershipAcceptState state) {
        return Partnership.builder()
                .id(req.getId())
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

    public void updatePartnershipAcceptState(PartnershipAcceptState acceptState) {
        this.acceptState = acceptState;
    }

    public void update(PartnershipReq dto, Store issStore, Store useStore, Menu menu) {
        this.issStore = issStore;
        this.useStore = useStore;
        this.menu = menu;
        this.couponAvailable = dto.getCouponAvailable();
        this.couponSale = dto.getCouponSale();
        this.startTime = dto.getStartTime();
        this.finishTime = dto.getFinishTime();
        this.availableTime = dto.getAvailableTime();
        this.issChargeAmount = dto.getIssChargeAmount();
        this.useChargeAmount = dto.getUseChargeAmount();
    }
}
