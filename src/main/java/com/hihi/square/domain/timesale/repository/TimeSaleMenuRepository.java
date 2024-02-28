package com.hihi.square.domain.timesale.repository;

import com.hihi.square.domain.timesale.entity.TimeSaleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TimeSaleMenuRepository extends JpaRepository<TimeSaleMenu, Integer> {
    @Query("select tsm from TimeSaleMenu tsm where tsm.timeSale.id=:saleId")
    Optional<TimeSaleMenu> findBySale(@Param("saleId") Integer saleId);

    @Query("select tsm from TimeSaleMenu tsm where tsm.timeSale.id=:saleId and tsm.menu.menuCategory.id=:mcId")
    Optional<TimeSaleMenu> findBySaleAndCategory(@Param("saleId") Integer saleId, @Param("mcId") Integer mcId);
}
