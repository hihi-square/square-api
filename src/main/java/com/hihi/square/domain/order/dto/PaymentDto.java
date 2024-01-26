package com.hihi.square.domain.order.dto;

import com.hihi.square.domain.order.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {
    Integer id;
    LocalDateTime payAt;
    PaymentStatus status;
    Integer orderId;
}
