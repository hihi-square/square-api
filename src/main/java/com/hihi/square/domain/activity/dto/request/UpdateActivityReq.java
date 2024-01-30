package com.hihi.square.domain.activity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateActivityReq {
    List<UpdateActivityDto> list;

    @Data
    public class UpdateActivityDto {
        Integer id;
        Boolean isMain;
        Integer depth;
    }
}