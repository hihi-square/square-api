package com.hihi.square.global.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hihi.square.global.jwt.exception.CustomJwtException;
import com.hihi.square.global.jwt.response.JwtExceptionResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (CustomJwtException e) {
			log.info("ex.getMessage()={}", e.getMessage());
			log.info("ex.getErrorCode()={}", e.getErrorCode());

			JwtExceptionResponse jwtExceptionResponse = JwtExceptionResponse.error(e.getErrorCode(), "");
			response.setStatus(e.getErrorCode().getStatus());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(), jwtExceptionResponse);
		}
	}
}