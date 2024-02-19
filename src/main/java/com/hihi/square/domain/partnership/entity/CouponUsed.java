package com.hihi.square.domain.partnership.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.buyer.entity.Buyer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "coupon_used")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class CouponUsed extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uic_id")
    private UserCoupon userCoupon; // 어떤 쿠폰이 발급되어 사용된건지.
    private Integer ordId; // 어떤 주문에서 사용되었는지

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buy_id")
    private Buyer buyer; // 어떤 유저가 사용하였는지
}
