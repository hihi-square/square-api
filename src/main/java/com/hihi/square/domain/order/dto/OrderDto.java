package com.hihi.square.domain.order.dto;

import com.hihi.square.domain.order.entity.OrderStatus;
import com.hihi.square.domain.order.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    Integer id;
    Integer totalPrice;
    Integer finalPrice;
    String request;
    OrderStatus status;
    Integer totalCnt;
    String rejectReason;
    Integer stoId;
    Integer couponId;
    List<OrderMenuDto> menuList;

    public static OrderDto toRes(Orders order, List<OrderMenuDto> menuList){
        return OrderDto.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .finalPrice(order.getFinalPrice())
                .request(order.getRequestInfo())
                .status(order.getStatus())
                .totalCnt(order.getTotalCnt())
                .rejectReason(order.getRejectReason())
                .menuList(menuList)
                .stoId(order.getStore().getUsrId())
                .couponId(order.getUserCoupon().getId())
                .build();
    }
}
