package com.hihi.square.domain.partnership.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.buyer.entity.Buyer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "issue_coupon")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class IssueCoupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uic_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "spa_id")
    private Partnership partnership; // 쿠폰 발급한 제휴 ID
    private Integer ordId; // 어떤 주문을 통해 쿠폰이 발급되었는지

    @ManyToOne
    @JoinColumn(name="buy_id")
    private Buyer buyer; // 쿠폰 사용 가능 유저

    private boolean isUsed; // 사용 완료 했는지

    private LocalDateTime expiredTime; // 사용 종료일
}
