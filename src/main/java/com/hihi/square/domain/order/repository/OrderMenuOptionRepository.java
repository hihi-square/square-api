package com.hihi.square.domain.order.repository;

import com.hihi.square.domain.order.entity.OrderMenuOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderMenuOptionRepository extends JpaRepository<OrderMenuOption, Integer> {
    @Query("select omo from OrderMenuOption omo where omo.orderMenu.id=:omId")
    List<OrderMenuOption> findAllByMenu(@Param("omId") Integer omId);
}
