package com.hihi.square.domain.partnership.repository;

import com.hihi.square.domain.partnership.entity.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnershipRepository extends JpaRepository<Partnership, Integer> {
}
