package com.hihi.square.domain.order.dto;

//import com.hihi.square.domain.coupon2.dto.CouponDto;
//import com.hihi.square.domain.coupon2.dto.UserCouponDto;
//import com.hihi.square.domain.coupon2.entity.Coupon;
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
    UserCouponDto coupon;
    List<OrderMenuDto> menuList;

    public static OrderDto toRes(Orders order, UserCouponDto coupon, List<OrderMenuDto> menuList){
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
                .coupon(coupon)
                .build();
    }
}
