package com.hihi.square.domain.partnership.repository;

import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartnershipRepository extends JpaRepository<Partnership, Integer> {
    List<Partnership> findAllByIssStore(Store issStore);

    List<Partnership> findAllByUseStore(Store useStore);

    @Query("select p from Partnership p where p.issStore = :store or p.useStore = :store")
    List<Partnership> findAllByStore(Store store);
}
