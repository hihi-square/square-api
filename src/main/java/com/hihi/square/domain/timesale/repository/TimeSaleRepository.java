package com.hihi.square.domain.timesale.repository;

import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.timesale.entity.TimeSale;
import com.hihi.square.domain.timesale.entity.TimeSaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeSaleRepository extends JpaRepository<TimeSale, Integer> {
    @Query("select ts from TimeSale ts where ts.id=:saleId and ts.status in (:statuses)")
    Optional<TimeSale> findById(@Param("saleId") Integer saleId, @Param("statuses") List<TimeSaleStatus> statuses);

    @Query("select ts from TimeSale ts where ts.store.usrId=:stoId")
    List<TimeSale> findByStore(@Param("stoId") Integer stoId);

    @Query("select ts from TimeSale ts where ts.store.usrId=:stoId and ts.status=:status")
    List<TimeSale> findByStoreAndStatus(@Param("stoId") Integer stoId, @Param("status") TimeSaleStatus status);

    @Query("select ts from TimeSale ts where ts.store.usrId=:stoId and :now between ts.startedAt and ts.finishedAt and ts.status in (:statuses)")
    List<TimeSale> findAllByStoreAndProgress(@Param("stoId") Integer stoId, @Param("now") LocalDateTime now, @Param("statuses") List<TimeSaleStatus> statuses);

    @Query("select ts from TimeSale ts where ts.id=:id and ts.status=:status")
    Optional<TimeSale> findByIdAndStatus(@Param("id") Integer id, @Param("status") TimeSaleStatus status);

    @Modifying
    @Query("update TimeSale ts set ts.status=:status where ts.id=:saleId")
    void updateSaleStatus(@Param("saleId") Integer saleId, @Param("status") TimeSaleStatus status);
}
