package com.hihi.square.domain.order.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.order.dto.OrderDto;
import com.hihi.square.domain.order.dto.PaymentDto;
import com.hihi.square.domain.order.entity.Orders;
import com.hihi.square.domain.order.event.OrderEvent;
import com.hihi.square.domain.order.service.OrderService;
import com.hihi.square.domain.order.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/order")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserOrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> addOrder(Authentication authentication, @RequestBody @Validated OrderDto orderDto){
        Integer usrId = Integer.parseInt(authentication.getName());
        orderService.addOrder(usrId, orderDto);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<?> selectOrder(Authentication authentication, @PathVariable(name = "order_id") @Validated Integer orderId){
        Integer usrId = Integer.parseInt(authentication.getName());
        OrderDto orderDto = orderService.selectOrder(usrId, orderId);
        return new ResponseEntity<>(CommonRes.success(orderDto), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<?> selectOrders(Authentication authentication){
        Integer usrId = Integer.parseInt(authentication.getName());
        List<OrderDto> orderDtos = orderService.selectOrdersByUserId(usrId);
        return new ResponseEntity<>(CommonRes.success(orderDtos), HttpStatus.ACCEPTED);
    }

    @PostMapping("/payment")
    public ResponseEntity<?> addPayment(Authentication authentication, @RequestBody @Validated PaymentDto paymentDto,
                                        HttpServletResponse response){
        Integer usrId = Integer.parseInt(authentication.getName());
        paymentService.addPayment(usrId, paymentDto, response);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }
}
