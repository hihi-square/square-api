package com.hihi.square.domain.order.service;

import com.hihi.square.domain.order.dto.OrderDto;
import com.hihi.square.domain.order.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    void addOrder(Integer usrId, OrderDto orderDto);
    OrderDto selectOrder(Integer usrId, Integer orderId);
    List<OrderDto> selectOrdersByUserId(Integer usrId);
    List<OrderDto> selectOrdersByStatus(Integer stoId, OrderStatus type);
    void acceptOrder(Integer stoId, Integer orderId);
    void rejectOrder(Integer stoId, Integer orderId, OrderDto orderDto);
}
