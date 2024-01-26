package com.hihi.square.domain.coupon.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_coupon")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class UserCoupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Enumerated(EnumType.STRING)
    CommonStatus status;
    @Column(name = "used_at")
    LocalDateTime usedAt;

    @ManyToOne
    @JoinColumn(name="ssc_id")
    Coupon coupon;
    @ManyToOne
    @JoinColumn(name="usr_id")
    User user;
}
