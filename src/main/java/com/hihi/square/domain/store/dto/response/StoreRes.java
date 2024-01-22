package com.hihi.square.domain.store.dto.response;

import com.hihi.square.domain.store.entity.Bank;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.user.dto.UserRes;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StoreRes extends UserRes {
	private String name;
	private String phone;
	private String storeName;
	private String storeContact;
	private String storeContact2;
	private String content;
	private String bank;
	private String account;
	private Integer dibs_count;
	private Integer review_count;
	private Double rating;
	private String address;
	private String detailAddress;

}