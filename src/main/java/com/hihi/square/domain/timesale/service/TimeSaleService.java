package com.hihi.square.domain.timesale.service;

import com.hihi.square.domain.timesale.dto.TimeSaleDto;
import com.hihi.square.domain.timesale.entity.TimeSaleStatus;

import java.util.List;

public interface TimeSaleService {
    void addSale(Integer stoId, TimeSaleDto timeSaleDto);
    void deleteSale(Integer stoId, Integer saleId);
    void updateSale(Integer stoId, Integer saleId, TimeSaleDto timeSaleDto);
    TimeSaleDto selectSale(Integer stoId, Integer saleId);

    List<TimeSaleDto> selectAllSale(Integer stoId);
    List<TimeSaleDto> selectAllSaleByStatus(Integer stoId, TimeSaleStatus status);
}
