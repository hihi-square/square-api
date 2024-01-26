package com.hihi.square.domain.store.dto;

import com.hihi.square.domain.category.dto.CategoryDto;
import com.hihi.square.domain.store.entity.Bank;
import com.hihi.square.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
	Integer id;
	String name;
	String phone;
	String email;
	String storeName;
	String storeContact;
	String storeContact2;
	String content;
	Bank bank;
	String account;
	String address;
	String detailAddress;
	Integer minPickUpTime;
	Integer maxPickUpTime;
	String image;
	CategoryDto category;

	public static StoreDto toRes(Store store, CategoryDto categoryDto){
		return StoreDto.builder()
				.name(store.getName())
				.phone(store.getPhone())
				.email(store.getEmail())
				.storeName(store.getStoreName())
				.storeContact(store.getStoreContact())
				.storeContact2(store.getStoreContact2())
				.content(store.getContent())
				.bank(store.getBank())
				.account(store.getAccount())
				.address(store.getAddress())
				.detailAddress(store.getDetailAddress())
				.minPickUpTime(store.getMinPickUpTime())
				.maxPickUpTime(store.getMaxPickUpTime())
				.image(store.getImage())
				.category(categoryDto)
				.build();
	}
}