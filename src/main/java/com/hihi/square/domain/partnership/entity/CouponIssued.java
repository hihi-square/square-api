package com.hihi.square.domain.partnership.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_issued")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class CouponIssued extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uic_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spa_id")
    private Partnership partnership; // 쿠폰 발급한 제휴 ID
    private Integer ordId; // 어떤 주문을 통해 쿠폰이 발급되었는지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="buy_id")
    private Buyer buyer; // 쿠폰 사용 가능 유저

    private boolean isUsed; // 사용 완료 했는지

    private LocalDateTime expiredTime; // 사용 종료일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sto_id")
    private Store store; // 어떤 가게에서 사용할 수 있는지

    @Min(0)
    private Integer couponAvailable; // 쿠폰 사용기준 금액 x원 이상 구매시 사용 가능한

    @Min(0)
    private Integer couponSale; // 쿠폰 금액 y원 할인 쿠폰
}
