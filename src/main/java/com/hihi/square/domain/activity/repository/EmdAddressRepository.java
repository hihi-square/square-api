package com.hihi.square.domain.activity.repository;

import com.hihi.square.domain.activity.entity.EmdAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmdAddressRepository extends JpaRepository<EmdAddress, Long> {
    @Query("select e from EmdAddress e join fetch e.siggAddress where e.id = :id")
    Optional<EmdAddress> findById(@Param("id") Long id);
}
