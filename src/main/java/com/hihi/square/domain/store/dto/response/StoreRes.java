package com.hihi.square.domain.store.dto.response;

import com.hihi.square.domain.activity.dto.response.EmdAddressRes;
import com.hihi.square.domain.store.entity.Bank;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.user.dto.UserRes;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper=false)
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

	public static StoreRes toRes(Store store) {
		return StoreRes.builder()
				.usrId(store.getUsrId())
				.profileImage(store.getProfileImage())
				.nickname(store.getNickname())
				.name(store.getName())
				.phone(store.getPhone())
				.storeName(store.getStoreName())
				.storeContact(store.getStoreContact())
				.storeContact2(store.getStoreContact2())
				.content(store.getContent())
				.bank(store.getBank().toString())
				.account(store.getAccount())
				.dibs_count(store.getDibs_count())
				.review_count(store.getReview_count())
				.rating(store.getRating())
				.address(store.getAddress().getSiggAddress().getName()+" "+store.getAddress().getName()+" "+store.getDetailAddress())
				.detailAddress(store.getDetailAddress())
				.build();
	}
}