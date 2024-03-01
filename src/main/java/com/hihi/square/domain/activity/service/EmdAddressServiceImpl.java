package com.hihi.square.domain.activity.service;

import com.hihi.square.domain.activity.entity.Activity;
import com.hihi.square.domain.activity.entity.EmdAddress;
import com.hihi.square.domain.activity.entity.EmdAddressDepth;
import com.hihi.square.domain.activity.repository.EmdAddressDepthRepository;
import com.hihi.square.domain.activity.repository.EmdAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmdAddressServiceImpl implements EmdAddressService {

    private final EmdAddressRepository emdAddressRepository;
    private final EmdAddressDepthRepository emdAddressDepthRepository;

    @Override
    public List<EmdAddress> getAllByActivity(Activity activity) {
        if (activity.getDepth() == 0) {
            List<EmdAddress> list = new ArrayList<>();
            list.add(activity.getEmdAddress());
            return list;
        }
        List<EmdAddress> list = emdAddressRepository.findByEmdAddressAndDepth(activity.getEmdAddress(), activity.getDepth());
        list.add(activity.getEmdAddress());
        return list;
    }

}
