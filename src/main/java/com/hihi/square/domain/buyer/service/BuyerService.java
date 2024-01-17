package com.hihi.square.domain.buyer.service;


import com.hihi.square.domain.buyer.dto.LoginRes;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface BuyerService {
    LoginRes login(Authentication authentication,  HttpServletResponse response);
}
