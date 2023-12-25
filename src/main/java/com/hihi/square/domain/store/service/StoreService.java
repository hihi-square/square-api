package com.hihi.square.domain.store.service;

import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.user.dto.request.LoginReq;
import com.hihi.square.domain.user.dto.request.SignUpReq;
import com.hihi.square.domain.user.dto.response.LoginRes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface StoreService {
    void save(Store store);
    void checkDuplicateUID(String uid);
    void join(SignUpReq signUpReq);
    LoginRes login(LoginReq loginReq, HttpServletResponse response);

    void recreateToken(HttpServletRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
