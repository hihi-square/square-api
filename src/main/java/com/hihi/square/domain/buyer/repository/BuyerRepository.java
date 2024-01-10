package com.hihi.square.domain.buyer.repository;

import com.hihi.square.domain.buyer.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Optional<Buyer> findByEmailAndMethod(String email, String method);
}
