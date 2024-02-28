package com.hihi.square.domain.store.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.store.dto.StoreDto;
import com.hihi.square.domain.store.dto.request.LoginReq;
import com.hihi.square.domain.store.dto.request.SignUpStoreReq;
import com.hihi.square.domain.store.dto.request.StoreFindReq;
import com.hihi.square.domain.store.dto.request.StorePasswordReq;
import com.hihi.square.domain.store.dto.response.LoginRes;
import com.hihi.square.domain.store.dto.response.StoreInfoRes;
import com.hihi.square.domain.store.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/store")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/join/check-id/{user_id}")
    public ResponseEntity<?> checkDuplicateUID(@PathVariable @Validated String uid){
        storeService.checkDuplicateUID(uid);
        return new ResponseEntity<>(CommonRes.success("Available User ID"), HttpStatus.ACCEPTED);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(
            @RequestBody @Validated SignUpStoreReq signUpStoreReq) {
        storeService.join(signUpStoreReq);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @PostMapping("/find-id")
    public ResponseEntity<?> findId(@RequestBody @Validated StoreFindReq storeFindReq) {
        Map<String, String> response = storeService.findId(storeFindReq);
        return new ResponseEntity<>(CommonRes.success(response), HttpStatus.ACCEPTED);
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

    //비밀번호 재설정
    @PostMapping("/reset-password")
    public ResponseEntity<?> updatePassword(Authentication authentication,
                                            @RequestBody @Validated StorePasswordReq storePasswordReq){
        Integer userId = Integer.parseInt(authentication.getName());
        storeService.updatePassword(userId, storePasswordReq.getPassword());
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{store_id}")
    public ResponseEntity<?> deleteStore(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication, @PathVariable(name = "store_id") @Validated Integer pathStoreId){
        Integer stoId = Integer.parseInt(authentication.getName());
        storeService.deleteStore(stoId, pathStoreId, request, response);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{store_id}")
    public ResponseEntity<?> selectStore(Authentication authentication, @PathVariable(name = "store_id") @Validated Integer pathStoreId){
        Integer stoId = Integer.parseInt(authentication.getName());
        StoreDto store = storeService.selectStore(stoId, pathStoreId);
        return new ResponseEntity<>(CommonRes.success(store), HttpStatus.ACCEPTED);
    }

    @GetMapping("/depth/{depth}")
    public ResponseEntity<?> selectStoresForChat(Authentication authentication, @PathVariable(name = "depth") @Validated Integer depth) {
        Integer stoId = Integer.parseInt(authentication.getName());
        List<StoreInfoRes> res = storeService.findAllStores(stoId, depth);
        return new ResponseEntity<>(CommonRes.success(res), HttpStatus.OK);
    }

}
