package com.hihi.square.domain.partnership.repository;

import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.partnership.entity.PartnershipStop;
import com.hihi.square.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PartnershipStopRepository extends JpaRepository<PartnershipStop, Integer> {
    @Query("select ps from PartnershipStop ps join fetch ps.store join fetch ps.partnership where ps.partnership = :partnership and ps.store = :store and ps.isFinished = :isFinished")
    Optional<PartnershipStop> findByPartnershipAndStoreAndIsFinished(Partnership partnership, Store store, Boolean isFinished);
    @Query("select ps from PartnershipStop ps join fetch ps.store join fetch ps.partnership where ps.partnership = :partnership and ps.isFinished = :isFinished")
    List<PartnershipStop> findAllByPartnershipAndIsFinished(Partnership partnership, boolean isFinished);
}
