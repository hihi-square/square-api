package com.hihi.square.domain.activity.service;

import com.hihi.square.domain.activity.dto.ActivityDto;
import com.hihi.square.domain.activity.dto.EmdAddressDto;
import com.hihi.square.domain.activity.dto.request.AddActivityReqDto;
import com.hihi.square.domain.activity.dto.request.UpdateActivityReqDto;

import java.util.List;

public interface ActivityService {

    List<ActivityDto> getAcitivityList(Integer buyerId);

    void addActivity(Integer buyerId, AddActivityReqDto req);

    void updateActivity(Integer buyerId, UpdateActivityReqDto req);
}
