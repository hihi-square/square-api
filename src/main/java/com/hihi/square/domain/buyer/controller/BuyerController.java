package com.hihi.square.domain.buyer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.buyer.dto.response.LoginRes;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.buyer.service.BuyerService;
import com.hihi.square.domain.buyer.service.BuyerServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@Slf4j
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class BuyerController {

    private final BuyerService buyerService;

    // 소셜 로그인이 끝나면 오는 컨트롤러
    // SuccessHandler를 활용하여 프론트 페이지로 redirect 시킴
    @ResponseBody
    @GetMapping("/login")
    public ResponseEntity login(Authentication authentication, HttpServletResponse response) throws IOException { //Authentication 을 DI (의존성 주입)
        log.info("구매자 로그인 성공");
        LoginRes loginResponseDto = buyerService.login(authentication, response);
        log.info("구매자 로그인 access token 생성 완료");
        return new ResponseEntity(CommonRes.success(loginResponseDto), HttpStatus.ACCEPTED);
    }
}