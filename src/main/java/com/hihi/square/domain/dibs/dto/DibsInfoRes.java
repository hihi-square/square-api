package com.hihi.square.domain.dibs.dto;

import com.hihi.square.domain.store.dto.response.StoreInfoRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DibsInfoRes {
    private Integer id;
    private StoreInfoRes store;
}
