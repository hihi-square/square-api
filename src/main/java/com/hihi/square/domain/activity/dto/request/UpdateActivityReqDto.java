package com.hihi.square.domain.activity.dto.request;

import com.hihi.square.domain.activity.dto.ActivityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateActivityReqDto {
    List<UpdateActivityDto> list;
}

