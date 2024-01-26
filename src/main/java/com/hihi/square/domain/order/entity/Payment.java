package com.hihi.square.domain.order.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.order.dto.PaymentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.query.Order;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "pay_at")
    LocalDateTime payAt;
    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "ord_id")
    Orders order;

    public static Payment toEntity(PaymentDto paymentDto, Orders order){
        return Payment.builder()
                .id(paymentDto.getId())
                .payAt(paymentDto.getPayAt())
                .status(paymentDto.getStatus())
                .order(order)
                .build();
    }
}
