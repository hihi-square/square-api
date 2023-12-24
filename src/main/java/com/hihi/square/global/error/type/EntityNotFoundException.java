package com.hihi.square.global.error.type;


import com.hihi.square.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

	public EntityNotFoundException(String message) {
		super(message, ErrorCode.ENTITY_NOT_FOUND);
	}
}