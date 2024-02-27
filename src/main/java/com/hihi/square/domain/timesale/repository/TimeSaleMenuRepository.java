package com.hihi.square.domain.timesale.repository;

import com.hihi.square.domain.timesale.entity.TimeSaleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TimeSaleMenuRepository extends JpaRepository<TimeSaleMenu, Integer> {
    @Query("select sm from TimeSaleMenu sm where sm.sale.id=:saleId")
    Optional<TimeSaleMenu> findBySale(@Param("saleId") Integer saleId);

}
