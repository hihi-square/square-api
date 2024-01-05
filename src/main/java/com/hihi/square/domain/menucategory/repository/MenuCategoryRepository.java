package com.hihi.square.domain.menucategory.repository;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Integer> {
    @Query("select mc from MenuCategory mc where mc.id=:mcId and mc.status in (:statuses)")
    Optional<MenuCategory> findById(@Param("mcId") Integer mcId, @Param("statuses") List<CommonStatus> statuses);

    @Query("select coalesce(max(mc.sequence), 0) + 1 from MenuCategory mc where mc.status in (:statuses)")
    Integer findSequence(@Param("statuses") List<CommonStatus> statuses);

    @Modifying
    @Query("update MenuCategory mc set mc.status='DELETE', mc.modifiedAt = current_timestamp where mc.id=:mcId")
    void deleteCategory(@Param("mcId") Integer mcId);

//    @Query("select mc from MenuCategory mc where mc.store.usrId=:stoId and mc.status in (:statuses)")
    @Query("select mc from MenuCategory mc where mc.store.usrId = :stoId and mc.status in :statuses order by mc.sequence asc, mc.modifiedAt desc")
    List<MenuCategory> findAllByStoreOrderBySequence(@Param("stoId") Integer stoId, @Param("statuses") List<CommonStatus> statuses);

    @Modifying
    @Query("update MenuCategory mc set mc.sequence=:sequence, mc.modifiedAt = current_timestamp where mc.id=:mcId")
    void updateSequence(@Param("mcId") Integer mcId, @Param("sequence") Integer sequence);
}
