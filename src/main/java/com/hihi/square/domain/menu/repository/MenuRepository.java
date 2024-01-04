package com.hihi.square.domain.menu.repository;

import com.hihi.square.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("select m from Menu m where m.menuCategory.id=:cId")
    List<Menu> findByCategory(@Param("cId") Integer cId);
}
