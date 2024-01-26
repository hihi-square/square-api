package com.hihi.square.domain.order.repository;

import com.hihi.square.domain.order.entity.OrderMenu;
import com.hihi.square.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Integer> {
    @Query("select om from OrderMenu om where om.order.id=:orderId")
    List<OrderMenu> findAllByOrder(@Param("orderId")Integer orderId);
}
