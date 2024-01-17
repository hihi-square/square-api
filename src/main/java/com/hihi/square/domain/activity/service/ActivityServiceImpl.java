package com.hihi.square.domain.activity.service;

import com.hihi.square.domain.activity.dto.ActivityDto;
import com.hihi.square.domain.activity.dto.EmdAddressDto;
import com.hihi.square.domain.activity.dto.request.AddActivityReqDto;
import com.hihi.square.domain.activity.entity.Activity;
import com.hihi.square.domain.activity.entity.EmdAddress;
import com.hihi.square.domain.activity.repository.ActivityRepository;
import com.hihi.square.domain.activity.repository.EmdAddressRepository;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.buyer.repository.BuyerRepository;
import com.hihi.square.global.error.ErrorCode;
import com.hihi.square.global.error.type.BusinessException;
import com.hihi.square.global.error.type.EntityNotFoundException;
import com.hihi.square.global.error.type.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityServiceImpl implements ActivityService{

    private final BuyerRepository buyerRepository;
    private final ActivityRepository activityRepository;
    private final EmdAddressRepository emdAddressRepository;

    @Override
    public List<ActivityDto> getAcitivityList(Integer buyerId) {
        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(()->new UserNotFoundException("User Not Found"));
        List<Activity> activityList = activityRepository.findAllByBuyer(buyer);
        List<ActivityDto> activityDtoList = new ArrayList<>();
        for(Activity activity:activityList) {
            activityDtoList.add(ActivityDto.toRes(activity));
        }
        return activityDtoList;
    }

    @Override
    public void addActivity(Integer buyerId, AddActivityReqDto req) {
        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(()->new UserNotFoundException("User Not Found"));
        EmdAddress emdAddress = emdAddressRepository.findById(req.getEmdId()).orElseThrow(()-> new EntityNotFoundException("EmdAddress Not Found"));
        // 활동지역은 최대 3개까지만 가능
        if (getAcitivityList(buyerId).size() == 3) throw new BusinessException("", ErrorCode.ADD_NOT_ALLOWED);
        Activity activity = Activity.builder()
                .emdAddress(emdAddress)
                .latitude(req.getLatitude())
                .longitude(req.getLongitude())
                .depth(0)
                .buyer(buyer).build();
        activityRepository.save(activity);
    }
}
