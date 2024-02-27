package com.hihi.square.domain.activity.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.activity.dto.request.AddActivityReq;
import com.hihi.square.domain.activity.dto.request.UpdateActivityReq;
import com.hihi.square.domain.activity.dto.response.ActivityRes;
import com.hihi.square.domain.activity.repository.EmdAddressDepthRepository;
import com.hihi.square.domain.activity.service.ActivityService;
import com.hihi.square.domain.activity.service.EmdAddressService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyers/activities")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
public class ActivityController {

    private final ActivityService activityService;
    private final EmdAddressService emdAddressService;

    // 활동반경 조회
    @GetMapping
    public ResponseEntity<List<ActivityRes>> getActivityList(Authentication authentication) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        List<ActivityRes> res = activityService.getAcitivityList(buyerId);
        return new ResponseEntity(CommonRes.success(res), HttpStatus.OK);
    }

    // 활동반경 추가
    @PostMapping
    public ResponseEntity<?> addActivity(Authentication authentication, @RequestBody @NotNull @Valid AddActivityReq req) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        activityService.addActivity(buyerId, req);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.CREATED);
    }

    // 활동반경 수정/삭제
    @PatchMapping
    public ResponseEntity<?> updateActivity(Authentication authentication, @RequestBody @Valid UpdateActivityReq req) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        activityService.updateActivity(buyerId, req);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.OK);
    }

    // 메인 활동반경 설정
    @PatchMapping("/main")
    public ResponseEntity<?> updateMainActivity(Authentication authentication, @RequestParam @NotNull @Valid Integer id) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        activityService.updateMainActivity(buyerId, id);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.OK);
    }

    @PostMapping("/data")
    public void data() {
        emdAddressService.data();
    }
}
