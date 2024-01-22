package com.hihi.square.domain.partnership.repository;

import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.partnership.entity.PartnershipStop;
import com.hihi.square.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartnershipStopRepository extends JpaRepository<PartnershipStop, Integer> {
    Optional<PartnershipStop> findByPartnershipAndStoreAndIsFinished(Partnership partnership, Store store, Boolean isFinished);
    List<PartnershipStop> findAllByPartnershipAndIsFinished(Partnership partnership, boolean isFinished);
}
