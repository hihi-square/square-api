package com.hihi.square.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessRes<T> {
	private int statusCode;
	private String message;
	private T data;

	public static <T> SuccessRes<T> success(T data) {
		return SuccessRes.<T>builder()
			.statusCode(200)
			.message("Success")
			.data(data)
			.build();
	}

//	public static <T> CommonResponseDto<T> error(ErrorCode errorCode, String message) {
//		return CommonResponseDto.<T>builder()
//			.statusCode(errorCode.getStatus())
//			.message(message)
//			.build();
//	}
}