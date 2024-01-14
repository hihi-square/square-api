package com.hihi.square.domain.buyer.service;


import com.hihi.square.domain.buyer.dto.response.LoginRes;
import com.hihi.square.domain.buyer.entity.LoginMethod;
import com.hihi.square.domain.store.dto.request.LoginReq;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface BuyerService {
    LoginRes login(Authentication authentication,  HttpServletResponse response);
}
