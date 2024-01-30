package com.hihi.square.domain.partnership.repository;

import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PartnershipRepository extends JpaRepository<Partnership, Integer> {
    List<Partnership> findAllByIssStore(Store issStore);

    List<Partnership> findAllByUseStore(Store useStore);

    @Query("select p from Partnership p join fetch p.issStore join fetch p.useStore join fetch p.menu join fetch p.proStore where p.issStore = :store or p.useStore = :store")
    List<Partnership> findAllByStore(Store store);

    @Query("select p from Partnership p join fetch p.issStore join fetch p.useStore join fetch p.menu join fetch p.proStore where (p.issStore = :store1 and p.useStore = :store2) or (p.issStore = :store2 and p.useStore = :store1)")
    List<Partnership> findAllByStores(Store store1, Store store2);

    @Query("select p from Partnership p join fetch p.issStore join fetch p.useStore join fetch p.menu join fetch p.proStore where (p.issStore = :store or p.useStore = :store) and :now between p.startTime and p.finishTime and p.acceptState = 'NORMAL'")
    List<Partnership> findAllByStoreAndProgress(Store store, LocalDateTime now);
}
