package com.hihi.square.domain.activity.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.activity.dto.ActivityDto;
import com.hihi.square.domain.activity.dto.EmdAddressDto;
import com.hihi.square.domain.activity.dto.request.AddActivityReqDto;
import com.hihi.square.domain.activity.dto.request.UpdateActivityReqDto;
import com.hihi.square.domain.activity.entity.EmdAddress;
import com.hihi.square.domain.activity.service.ActivityService;
import com.hihi.square.domain.buyer.entity.Buyer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyer/activity")
@Slf4j
@RequiredArgsConstructor
public class ActivityController {


    private final ActivityService activityService;

    @GetMapping
    public ResponseEntity getActivityList(Authentication authentication) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        List<ActivityDto> res = activityService.getAcitivityList(buyerId);
        return new ResponseEntity(CommonRes.success(res), HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity addActivity(Authentication authentication, @RequestBody @NotNull AddActivityReqDto req) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        activityService.addActivity(buyerId, req);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity patchActivity(Authentication authentication, @RequestBody UpdateActivityReqDto req) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        System.out.println(req);
        activityService.updateActivity(buyerId, req);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.ACCEPTED);
    }
}
