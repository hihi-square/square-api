package com.hihi.square.domain.buyer.controller;

import com.hihi.square.common.SuccessRes;
import com.hihi.square.domain.buyer.service.BuyerService;
import com.hihi.square.domain.buyer.service.BuyerServiceImpl;
import com.hihi.square.domain.user.dto.request.LoginReq;
import com.hihi.square.domain.user.dto.response.LoginRes;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class BuyerController {

//    private final BuyerService buyerService;

    @PostMapping("/login/oauth/kakao")
    public ResponseEntity<?> login(@RequestParam String code
           ) {
        System.out.println("code"+code);
        return new ResponseEntity<>(SuccessRes.success(11), HttpStatus.ACCEPTED);
    }
}
