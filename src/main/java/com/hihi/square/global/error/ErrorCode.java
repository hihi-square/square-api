package com.hihi.square.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
	// User
	INVALID_TOKEN(400, "U001", "Invalid Token"),
	USER_NOT_FOUND(400, "U002", "User Not Found"),
	ACCESS_DENIED(403, "U003", "Access Denied"),
	EXPIRED_TOKEN(409, "U004", "Expired Token"),
	DUPLICATED_USER(400, "U005", "User is duplicated"),
	TOKEN_NOT_FOUND(400, "U005", "Token Not Found"),
	TOKEN_NOT_SAME(401, "U006", "Token Not Same"),
	TOKEN_NOT_EXISTS(400, "U007", "Token Not Exists"),
	PASSWORD_NOT_MATCH(400, "U008", "Password doesn't matched"),
	RE_LOGIN(409, "U009", "ReLogin"),
	COOKIE_NOT_FOUND(400, "U010", "Cookie Not Found"),
	USER_MISMATCH(400, "U011", "User Mismatch"),

	// Common
	INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
	METHOD_NOT_ALLOWED(405, "C002", "Invalid Input Value"),
	ENTITY_NOT_FOUND(400, "C003", "Entity Not Found"),
	INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
	INVALID_TYPE_VALUE(400, "C005", "Invalid Type Value"),
	HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
	REQUIRED_PARAMETER_MISSING(411, "C007", "Required Parameter is missing"),
	DELETE_NOT_ALLOWED(400, "C008", "Delete Not Allowed"),
	ADD_NOT_ALLOWED(400, "C009", "Add Not Allowed"),
	UPDATE_NOT_ALLOWED(400, "C010", "UPDATE Not Allowed"),
	SSE_NOT_CONNECT(403, "C009", "SSE Not Connect");

	private final String code;
	private final String message;
	private final int status;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}

}