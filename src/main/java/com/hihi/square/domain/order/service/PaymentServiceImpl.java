package com.hihi.square.domain.order.service;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.order.dto.PaymentDto;
import com.hihi.square.domain.order.entity.OrderStatus;
import com.hihi.square.domain.order.entity.Orders;
import com.hihi.square.domain.order.entity.Payment;
import com.hihi.square.domain.order.entity.PaymentStatus;
import com.hihi.square.domain.order.event.OrderEvent;
import com.hihi.square.domain.order.repository.OrderRepository;
import com.hihi.square.domain.order.repository.PaymentRepository;
import com.hihi.square.domain.user.entity.User;
import com.hihi.square.domain.user.entity.UserStatus;
import com.hihi.square.domain.user.repository.UserRepository;
import com.hihi.square.global.error.type.EntityNotFoundException;
import com.hihi.square.global.error.type.InvalidValueException;
import com.hihi.square.global.error.type.UserMismachException;
import com.hihi.square.global.error.type.UserNotFoundException;
import com.hihi.square.global.sse.SseService;
import com.hihi.square.global.sse.entity.NotificationType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.MethodNotAllowedException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService{
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void addPayment(Integer usrId, PaymentDto paymentDto, HttpServletResponse response) {
        //1. 유저, 주문 존재 여부 확인
        User user = userRepository.findByUserId(usrId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("User Not Found"));
        Orders order = orderRepository.findById(paymentDto.getOrderId(), OrderStatus.REGISTER).orElseThrow(
                () -> new EntityNotFoundException("Order Not Found"));
        //2. 주문자와 로그인한 유저 정보 일치 여부
        if(order.getUser().getUsrId() != usrId) throw new UserMismachException("User, Order User MisMatch");

        //4. 결제 정보 저장 + 주문 상태 변경
        LocalDateTime localDateTime = LocalDateTime.now();
        paymentDto.setPayAt(localDateTime);
        Payment payment = Payment.toEntity(paymentDto, order);
        payment = paymentRepository.save(payment);
        orderRepository.updateStatus(order.getId(), OrderStatus.WAIT);

        //5. 알림 전송
        if(payment.getStatus() == PaymentStatus.COMPLETED) eventPublisher.publishEvent(new OrderEvent(OrderStatus.WAIT, order, "주문이 도착했습니다."));
    }
}
