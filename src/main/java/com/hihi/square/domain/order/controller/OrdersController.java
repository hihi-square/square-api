package com.hihi.square.domain.order.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.order.dto.OrderDto;
import com.hihi.square.domain.order.service.OrderService;
import com.hihi.square.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;

    @GetMapping("/{order_id}")
    public ResponseEntity<?> selectOrder(Authentication authentication,  @PathVariable(name = "order_id") @Validated Integer orderId){
        Integer userId = Integer.parseInt(authentication.getName());
        OrderDto orderDto = orderService.selectOrder(userId, orderId);
        return new ResponseEntity(CommonRes.success(orderDto), HttpStatus.ACCEPTED);
    }

    @GetMapping("/status")
    public ResponseEntity<?> selectOrdersByStatus(Authentication authentication, @RequestBody @Validated OrderDto orderDto){
        Integer userId = Integer.parseInt(authentication.getName());
        List<OrderDto> orderDtos = orderService.selectOrdersByStatus(userId, orderDto.getStatus());
        return new ResponseEntity(CommonRes.success(orderDtos), HttpStatus.ACCEPTED);
    }

    @GetMapping("/accept/{order_id}")
    public ResponseEntity<?> acceptOrder(Authentication authentication,  @PathVariable(name = "order_id") @Validated Integer orderId){
        Integer userId = Integer.parseInt(authentication.getName());
        orderService.acceptOrder(userId, orderId);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @GetMapping("/reject/{order_id}")
    public ResponseEntity<?> rejectOrder(Authentication authentication,  @PathVariable(name = "order_id") @Validated Integer orderId,
                                         @RequestBody @Validated OrderDto orderDto){
        Integer userId = Integer.parseInt(authentication.getName());
        orderService.rejectOrder(userId, orderId, orderDto);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.ACCEPTED);
    }
}
