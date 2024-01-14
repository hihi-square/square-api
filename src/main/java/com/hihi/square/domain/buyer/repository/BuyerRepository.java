package com.hihi.square.domain.buyer.repository;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.buyer.entity.LoginMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Optional<Buyer> findByUidAndMethod(String uid, LoginMethod method);
}
