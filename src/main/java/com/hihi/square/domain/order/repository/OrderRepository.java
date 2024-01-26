package com.hihi.square.domain.order.repository;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.order.entity.OrderStatus;
import com.hihi.square.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("select o from Orders o where o.id=:orderId")
    Optional<Orders> findById(@Param("orderId") Integer orderId);

    @Query("select o from Orders o where o.id=:orderId and o.status=:status")
    Optional<Orders> findById(@Param("orderId") Integer orderId, @Param("status") OrderStatus status);


    @Query("select o from Orders o where o.user.usrId=:usrId")
    List<Orders> findAllByUserId(@Param("usrId") Integer usrId);

    @Query("select o from Orders o where o.store.usrId=:stoId and o.status=:status")
    List<Orders> findAllByUserAndType(@Param("stoId") Integer stoId, @Param("status") OrderStatus status);

    @Modifying
    @Query("update Orders o set o.status=:status where o.id=:orderId")
    void updateStatus(@Param("orderId") Integer orderId, @Param("status") OrderStatus status);

    @Modifying
    @Query("update Orders o set o.status=:status, o.rejectReason=:reason where o.id=:orderId")
    void updateStatusToReject(@Param("orderId") Integer orderId, @Param("status") OrderStatus status, @Param("reason") String reason);
}
