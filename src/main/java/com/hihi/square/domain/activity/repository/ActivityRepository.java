package com.hihi.square.domain.activity.repository;


import com.hihi.square.domain.activity.entity.Activity;
import com.hihi.square.domain.buyer.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    @Query("select a from Activity a JOIN FETCH a.emdAddress JOIN FETCH a.emdAddress.siggAddress WHERE a.buyer = :buyer")
    List<Activity> findAllByBuyer(Buyer buyer);
    Activity findByBuyerAndIsMainTrue(Buyer buyer);
}
