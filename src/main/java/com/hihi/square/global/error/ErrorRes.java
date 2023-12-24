package com.hihi.square.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorRes {

	private String message;
	private int status;
	private List<FieldError> errors;
	private String code;

	private ErrorRes(final ErrorCode code, final List<FieldError> errors) {
		this.message = code.getMessage();
		this.status = code.getStatus();
		this.errors = errors;
		this.code = code.getCode();
	}

	private ErrorRes(final ErrorCode code) {
		this.message = code.getMessage();
		this.status = code.getStatus();
		this.code = code.getCode();
		this.errors = new ArrayList<>();
	}

	public static ErrorRes of(final ErrorCode code, final BindingResult bindingResult) {
		return new ErrorRes(code, FieldError.of(bindingResult));
	}

	public static ErrorRes of(final ErrorCode code) {
		return new ErrorRes(code);
	}

	public static ErrorRes of(final ErrorCode code, final List<FieldError> errors) {
		return new ErrorRes(code, errors);
	}

	public static ErrorRes of(MethodArgumentTypeMismatchException e) {
		final String value = e.getValue() == null ? "" : e.getValue().toString();
		final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
		return new ErrorRes(ErrorCode.INVALID_TYPE_VALUE, errors);
	}

	public static ErrorRes of(AccessDeniedException e) {
		return new ErrorRes(ErrorCode.ACCESS_DENIED);
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class FieldError {
		private String field;
		private String value;
		private String reason;

		private FieldError(final String field, final String value, final String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}

		public static List<FieldError> of(final String field, final String value, final String reason) {
			List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, reason));
			return fieldErrors;
		}

		private static List<FieldError> of(final BindingResult bindingResult) {
			final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
			return fieldErrors.stream()
							  .map(error -> new FieldError(error.getField(),
								  error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
								  error.getDefaultMessage()))
							  .collect(Collectors.toList());
		}
	}

}