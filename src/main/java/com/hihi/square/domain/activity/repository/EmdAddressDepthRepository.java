package com.hihi.square.domain.activity.repository;

import com.hihi.square.domain.activity.entity.EmdAddress;
import com.hihi.square.domain.activity.entity.EmdAddressDepth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface EmdAddressDepthRepository extends JpaRepository<EmdAddressDepth, Long> {
    @Query("select ed from EmdAddressDepth ed where ed.emdAddress1.id = :emd_id and ed.depth = :depth")
    List<EmdAddressDepth> findByEmdAddress1AndDepth(Long emd_id, int depth);
    boolean existsByEmdAddress1AndEmdAddress2(EmdAddress emdAddress1, EmdAddress emdAddress2);
}