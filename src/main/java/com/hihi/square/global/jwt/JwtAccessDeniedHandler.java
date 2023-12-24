package com.hihi.square.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hihi.square.global.error.ErrorRes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

//필요한 권한이 존재하지 않는 경우, 403 Forbidden Error Return
@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
   @Override
   public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
      //필요한 권한이 없이 접근하려 할때 403
      log.info("Responding with forbidden error. Message := {}", accessDeniedException.getMessage());
      ErrorRes errorResponse = ErrorRes.of(accessDeniedException);
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
   }
}