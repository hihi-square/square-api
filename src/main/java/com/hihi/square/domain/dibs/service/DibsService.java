package com.hihi.square.domain.dibs.service;

import com.hihi.square.domain.dibs.dto.DibsAllRes;

import java.util.List;

public interface DibsService {
    DibsAllRes getAllDibsList(Integer buyerId);
    void addDibs(Integer buyerId, Integer storeId);
    void removeDibs(Integer buyerId, Integer dibsId);
}
