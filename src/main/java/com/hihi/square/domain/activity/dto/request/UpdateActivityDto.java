package com.hihi.square.domain.activity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateActivityDto {
    Integer id;
    Boolean isMain;
    Integer depth;
}
