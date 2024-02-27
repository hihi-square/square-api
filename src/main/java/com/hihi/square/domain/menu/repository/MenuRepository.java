package com.hihi.square.domain.menu.repository;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("select m from Menu m where m.id=:menuId and m.status in (:statuses)")
    Optional<Menu> findById(@Param("menuId") Integer menuId, @Param("statuses") List<CommonStatus> statuses);

    @Query("select m from Menu m where m.id=:menuId and m.status=:status")
    Optional<Menu> findById(@Param("menuId") Integer menuId, @Param("status") CommonStatus status);

    @Query("select m from Menu m where m.store.usrId = :stoId and m.status in :statuses order by m.sequence asc, m.modifiedAt desc")
    List<Menu> findAllByStoreOrderBySequence(@Param("stoId") Integer stoId, @Param("statuses") List<CommonStatus> statuses);

    @Query("select m from Menu m where m.store.usrId = :stoId and m.status=:status order by m.sequence asc, m.modifiedAt desc")
    List<Menu> findAllByStoreOrderBySequence(@Param("stoId") Integer stoId, @Param("status") CommonStatus status);

    @Query("select m from Menu m where m.menuCategory.id=:cId")
    List<Menu> findByCategory(@Param("cId") Integer cId);

    @Query("select m from Menu m where m.id=:id and m.store.usrId=:stoId")
    Optional<Menu> findByIdAndStore(@Param("id") Integer id, @Param("stoId") Integer stoId);

    @Query("select coalesce(max(m.sequence), 0) + 1 from Menu m where m.status in (:statuses)")
    Integer findSequence(@Param("statuses") List<CommonStatus> statuses);

    @Modifying
    @Query("update Menu m set m.status=:status, m.modifiedAt = current_timestamp where m.id=:menuId")
    void deleteMenu(@Param("menuId") Integer menuId, @Param("status") CommonStatus status);

    @Modifying
    @Query("update Menu m set m.sequence=:sequence, m.menuCategory=:mc, m.status=:status, m.modifiedAt = current_timestamp where m.id=:menuId")
    void updateSequence(@Param("menuId") Integer menuId, @Param("sequence") Integer sequence, @Param("mc") MenuCategory mc, @Param("status") CommonStatus status);
}
