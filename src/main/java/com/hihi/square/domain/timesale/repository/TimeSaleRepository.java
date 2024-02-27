package com.hihi.square.domain.timesale.repository;

import com.hihi.square.domain.timesale.entity.TimeSale;
import com.hihi.square.domain.timesale.entity.TimeSaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TimeSaleRepository extends JpaRepository<TimeSale, Integer> {
    @Query("select s from TimeSale s where s.id=:saleId and s.status in (:statuses)")
    Optional<TimeSale> findById(@Param("saleId") Integer saleId, @Param("statuses") List<TimeSaleStatus> statuses);

    @Query("select s from TimeSale s where s.store.usrId=:stoId")
    List<TimeSale> findByStore(@Param("stoId") Integer stoId);

    @Query("select s from TimeSale s where s.store.usrId=:stoId and s.status=:status")
    List<TimeSale> findByStoreAndStatus(@Param("stoId") Integer stoId, @Param("status") TimeSaleStatus status);

    @Query("select s from TimeSale s where s.id=:id and s.status=:status")
    Optional<TimeSale> findByIdAndStatus(@Param("id") Integer id, @Param("status") TimeSaleStatus status);

    @Modifying
    @Query("update TimeSale s set s.status=:status where s.id=:saleId")
    void updateSaleStatus(@Param("saleId") Integer saleId, @Param("status") TimeSaleStatus status);
}
