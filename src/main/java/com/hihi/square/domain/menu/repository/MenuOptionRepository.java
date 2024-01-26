package com.hihi.square.domain.menu.repository;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.entity.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Integer> {

    @Query("select mo from MenuOption mo where mo.id=:moId and mo.status in :statuses")
    Optional<MenuOption> findById(@Param("moId") Integer moId, @Param("statuses") List<CommonStatus> statuses);

    @Query("select mo from MenuOption mo where mo.id=:moId and mo.status =:status")
    Optional<MenuOption> findById(@Param("moId") Integer moId, @Param("status") CommonStatus status);

    @Query("select mo from MenuOption mo where mo.menu.id=:menuId")
    List<MenuOption> findAllByMenu(@Param("menuId") Integer menuId);
}
