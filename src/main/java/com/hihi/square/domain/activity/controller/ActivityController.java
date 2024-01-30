package com.hihi.square.domain.activity.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.activity.dto.request.AddActivityReq;
import com.hihi.square.domain.activity.dto.request.UpdateActivityReq;
import com.hihi.square.domain.activity.dto.response.ActivityRes;
import com.hihi.square.domain.activity.service.ActivityService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<ActivityRes>> getActivityList(Authentication authentication) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        List<ActivityRes> res = activityService.getAcitivityList(buyerId);
        return new ResponseEntity(CommonRes.success(res), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addActivity(Authentication authentication, @RequestBody @NotNull @Valid AddActivityReq req) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        activityService.addActivity(buyerId, req);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> updateActivity(Authentication authentication, @RequestBody @Valid UpdateActivityReq req) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        activityService.updateActivity(buyerId, req);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.OK);
    }

    @PatchMapping("/main")
    public ResponseEntity<?> updateMainActivity(Authentication authentication, @RequestParam @NotNull @Valid Integer id) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        activityService.updateMainActivity(buyerId, id);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.OK);
    }
}
