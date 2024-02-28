package com.hihi.square.domain.dibs.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DibsAllRes {
    private List<DibsInfoRes> list = new ArrayList<>();
}
