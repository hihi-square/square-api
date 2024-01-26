package com.hihi.square.domain.order.service;

import com.hihi.square.domain.order.dto.PaymentDto;
import com.hihi.square.domain.order.entity.Orders;
import jakarta.servlet.http.HttpServletResponse;

public interface PaymentService {
    void addPayment(Integer usrId, PaymentDto paymentDto, HttpServletResponse response);
}
