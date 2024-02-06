package com.hihi.square.domain.store.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpStoreReq {

	@NotEmpty(message = "아이디는 필수 입력값입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "아이디 형식에 맞지 않습니다.")
	private String uid;

	@NotEmpty(message = "비밀번호는 필수 입력값입니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String password;

	@NotEmpty(message = "이름은 필수 입력값입니다.")
	private String name;

	@NotEmpty(message = "이메일은 필수 입력값입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 형식에 맞지 않습니다.")
	private String email;

	@NotEmpty(message = "핸드폰번호는 필수 입력값입니다.")
	private String phone;

	@NotEmpty(message = "가게 이름은 필수 입력값입니다.")
	private String storeName;

	private String content;

	@NotEmpty(message = "가게 전화번호는 필수 입력값입니다.")
	private String storeContact;

	private String storeContact2;

	@NotEmpty(message = "주소는 필수 입력값입니다.")
	private Long bCode;

	@NotEmpty(message = "상세 주소는 필수 입력값입니다.")
	private String detailAddress;

	private Double latitude;
	private Double longitude;
}