package com.hihi.square.domain.store.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.store.service.StoreService;
import com.hihi.square.domain.store.dto.request.LoginReq;
import com.hihi.square.domain.store.dto.request.SignUpReq;
import com.hihi.square.domain.store.dto.response.LoginRes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/join/check-id/{user_id}")
    public ResponseEntity<?> checkDuplicateUID(@PathVariable @Validated String uid){
        storeService.checkDuplicateUID(uid);
        return new ResponseEntity<>(CommonRes.success("Available User ID"), HttpStatus.ACCEPTED);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(
            @RequestBody @Validated SignUpReq signUpReq) {
        storeService.join(signUpReq);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Validated LoginReq loginReq, HttpServletResponse response) {
        LoginRes loginResponseDto = storeService.login(loginReq, response);

        return new ResponseEntity<>(CommonRes.success(loginResponseDto), HttpStatus.ACCEPTED);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response){
        storeService.recreateToken(request, response);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        storeService.logout(request, response);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(HttpServletRequest request, HttpServletResponse response){
        storeService.deleteStore(request, response);

        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

//    @GetMapping("/test")
//    public ResponseEntity<?> test(Authentication authentication){
//        return new ResponseEntity<>(CommonRes.success("test"), HttpStatus.ACCEPTED);
//    }
}
