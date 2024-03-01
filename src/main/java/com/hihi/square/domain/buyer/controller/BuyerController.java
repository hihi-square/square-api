package com.hihi.square.domain.buyer.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.buyer.dto.response.LoginRes;
import com.hihi.square.domain.buyer.service.BuyerService;
import com.hihi.square.domain.store.dto.response.StoreInfoRes;
import com.hihi.square.domain.store.dto.response.StoreSearchInfoDto;
import com.hihi.square.domain.store.service.StoreService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/buyers")
@RequiredArgsConstructor
public class BuyerController {

    private final BuyerService buyerService;
    private final StoreService storeService;

    // 소셜 로그인이 끝나면 오는 컨트롤러
    // SuccessHandler를 활용하여 프론트 페이지로 redirect 시킴
    @ResponseBody
    @GetMapping("/login")
    public ResponseEntity<LoginRes> login(Authentication authentication, HttpServletResponse response) throws IOException { //Authentication 을 DI (의존성 주입)
        log.info("구매자 로그인 성공");
        LoginRes loginResponseDto = buyerService.login(authentication, response);
        log.info("구매자 로그인 access token 생성 완료");
        return new ResponseEntity(CommonRes.success(loginResponseDto), HttpStatus.OK);
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<LoginRes> getStoresInfo(Authentication authentication, @PathVariable Integer storeId) throws IOException { //Authentication 을 DI (의존성 주입)
        Integer buyerId = Integer.parseInt(authentication.getName());
        StoreInfoRes storeInfoRes = storeService.findInfoForBuyer(buyerId, storeId);
        return new ResponseEntity(CommonRes.success(storeInfoRes), HttpStatus.OK);
    }

    @GetMapping("/stores/search")
    public ResponseEntity searchStores(Authentication authentication, @RequestParam("category") Integer category, @RequestParam("orderBy") String orderBy, @RequestParam("timesale") boolean timesale, @RequestParam("partnership") boolean partnership, @RequestParam("dibs") boolean dibs, @RequestParam("long") @DefaultValue("0.0") Double longitude, @RequestParam("lat") @DefaultValue("0.0") Double latitude) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        List<StoreSearchInfoDto> res = storeService.searchStores(buyerId, category, orderBy, timesale, partnership, dibs, longitude, latitude);
        return new ResponseEntity(CommonRes.success(res), HttpStatus.OK);
    }
}