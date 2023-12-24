package com.hihi.square.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRes {
	private Integer userId;

	private String name;

	private String email;

	private String phoneNumber;

	private String role;
}