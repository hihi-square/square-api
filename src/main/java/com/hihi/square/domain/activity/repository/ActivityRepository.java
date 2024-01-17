package com.hihi.square.domain.activity.repository;


import com.hihi.square.domain.activity.dto.ActivityDto;
import com.hihi.square.domain.activity.entity.Activity;
import com.hihi.square.domain.buyer.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findAllByBuyer(Buyer buyer);
}
