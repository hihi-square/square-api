package com.hihi.square.domain.activity.service;

import com.hihi.square.domain.activity.dto.ActivityDto;
import com.hihi.square.domain.activity.dto.EmdAddressDto;
import com.hihi.square.domain.activity.dto.request.AddActivityReqDto;
import com.hihi.square.domain.activity.dto.request.UpdateActivityDto;
import com.hihi.square.domain.activity.dto.request.UpdateActivityReqDto;
import com.hihi.square.domain.activity.entity.Activity;
import com.hihi.square.domain.activity.entity.EmdAddress;
import com.hihi.square.domain.activity.repository.ActivityRepository;
import com.hihi.square.domain.activity.repository.EmdAddressRepository;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.buyer.repository.BuyerRepository;
import com.hihi.square.global.error.ErrorCode;
import com.hihi.square.global.error.type.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void addActivity(Integer buyerId, AddActivityReqDto req) {
        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(()->new UserNotFoundException("User Not Found"));
        EmdAddress emdAddress = emdAddressRepository.findById(req.getEmdId()).orElseThrow(()-> new EntityNotFoundException("EmdAddress Not Found"));
        // 활동지역은 최대 3개까지만 가능
        if (getAcitivityList(buyerId).size() == 3) throw new BusinessException("", ErrorCode.ADD_NOT_ALLOWED);

        // 새로 추가하는 활동지역이 기본적으로 메인이 됨
        List<Activity> activityList = activityRepository.findAllByBuyer(buyer);
        for(Activity a : activityList) {
            if (a.isMain()) a.updateIsMain(false);
        }
        Activity activity = Activity.builder()
                .emdAddress(emdAddress)
                .latitude(req.getLatitude())
                .longitude(req.getLongitude())
                .depth(0)
                .isMain(true)
                .buyer(buyer).build();
        activityRepository.save(activity);

    }

    @Override
    @Transactional
    public void updateActivity(Integer buyerId, UpdateActivityReqDto req) {
        // 활동 반경은 적어도 하나 있어야 한다.
        if (req.getList().isEmpty()) throw new InvalidValueException("There Must Be At Least One Object");

        // 먼저 구매자를 가져온다.
        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(()->new UserNotFoundException("User Not Found"));

        // 해당 구매자의 activity list를 가져온다.
        List<Activity> activityList = activityRepository.findAllByBuyer(buyer);
        boolean checked = false;

        // 각 req 리스트를 돌며
        for(UpdateActivityDto activityDto : req.getList()) {
            Activity activity = activityRepository.findById(activityDto.getId()).orElseThrow(() -> new EntityNotFoundException("Activity Not Found"));
            if (!activity.getBuyer().getUsrId().equals(buyer.getUsrId())) throw new UserMismachException("User mismatch");
            activity.update(activityDto);
            for(int i=0;i<activityList.size();i++) {
                if (activityList.get(i).getId() == activity.getId()) {
                    activityList.remove(i);
                    break;
                }
            }
            // Main Activity는 하나만 가능
            if (activityDto.getIsMain() && checked) throw new InvalidValueException("Main Activity Is Only One");
            if (activityDto.getIsMain()) checked = true;
        }

        // Main Activity는 적어도 하나 있어야 한다.
        if (!checked) throw new InvalidValueException("Main Activity Is Required");

        // req 리스트에 없음은 사용자가 삭제했음을 의미하므로 삭제해준다.
        for(Activity activity : activityList) {
            activityRepository.delete(activity);
        }
    }

    @Override
    @Transactional
    public void updateMainActivity(Integer buyerId, Integer activityId) {
        // 구매자, 활동구역 객체 가져오기
        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(()->new UserNotFoundException("User Not Found"));
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new EntityNotFoundException("Activity Not Found"));

        // 만약 구매자와 활동구역의 유저가 같지 않으면 예외 처리
        if (!activity.getBuyer().equals(buyer)) throw new UserMismachException("User Mismatch");

        // 기존 main activity false 처리
        Activity pastMainActivity = activityRepository.findByBuyerAndIsMainTrue(buyer);
        pastMainActivity.updateIsMain(false);

        // 새로운 main activity 설정
        activity.updateIsMain(true);
    }
}
