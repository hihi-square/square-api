package com.hihi.square.domain.timesale.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hihi.square.domain.timesale.entity.TimeSale;
import com.hihi.square.domain.timesale.entity.TimeSaleStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TimeSaleDto {
    Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime finishedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime realFinishedAt;
    TimeSaleStatus status;
    TimeSaleMenuDto saleMenu;

    public static TimeSaleDto toRes(TimeSale timeSale, TimeSaleMenuDto saleMenu){
        return TimeSaleDto.builder()
                .id(timeSale.getId())
                .startedAt(timeSale.getStartedAt())
                .finishedAt(timeSale.getFinishedAt())
                .realFinishedAt(timeSale.getRealFinishedAt())
                .status(timeSale.getStatus())
                .saleMenu(saleMenu)
                .build();
    }
}
