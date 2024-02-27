package com.hihi.square.domain.activity.service;

import com.hihi.square.domain.activity.entity.Activity;
import com.hihi.square.domain.activity.entity.EmdAddress;

import java.util.List;

public interface EmdAddressService {
    List<EmdAddress> getAllByActivity(Activity activity);
}
