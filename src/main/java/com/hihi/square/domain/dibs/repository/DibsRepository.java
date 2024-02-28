package com.hihi.square.domain.dibs.repository;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.dibs.entity.Dibs;
import com.hihi.square.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DibsRepository extends JpaRepository<Dibs, Integer> {
    @Query("select d from Dibs d join fetch d.store join fetch d.store.address join fetch d.store.address.siggAddress where d.buyer = :buyer")
    List<Dibs> findByBuyer(Buyer buyer);

    boolean existsByBuyerAndStore(Buyer buyer, Store store);

    void removeByBuyerAndId(Buyer buyer, Integer id);

}
