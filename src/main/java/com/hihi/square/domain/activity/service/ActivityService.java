package com.hihi.square.domain.activity.service;

import com.hihi.square.domain.activity.dto.request.AddActivityReq;
import com.hihi.square.domain.activity.dto.request.UpdateActivityReq;
import com.hihi.square.domain.activity.dto.response.ActivityRes;

import java.util.List;

public interface ActivityService {

    List<ActivityRes> getAcitivityList(Integer buyerId);

    void addActivity(Integer buyerId, AddActivityReq req);

    void updateActivity(Integer buyerId, UpdateActivityReq req);

    void updateMainActivity(Integer buyerId, Integer activityId);
}
