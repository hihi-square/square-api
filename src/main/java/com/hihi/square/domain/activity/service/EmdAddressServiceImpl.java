package com.hihi.square.domain.activity.service;

import com.hihi.square.domain.activity.entity.Activity;
import com.hihi.square.domain.activity.entity.EmdAddress;
import com.hihi.square.domain.activity.entity.EmdAddressDepth;
import com.hihi.square.domain.activity.repository.EmdAddressDepthRepository;
import com.hihi.square.domain.activity.repository.EmdAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class EmdAddressServiceImpl implements EmdAddressService {

    private final EmdAddressRepository emdAddressRepository;
    private final EmdAddressDepthRepository emdAddressDepthRepository;

    @Override
    public List<EmdAddress> getAllByActivity(Activity activity) {
//        List<EmdAddress> list = emdAddressRepository.findAll();
//        for(EmdAddress e : list) {
//            Queue<EmdAddress> queue = new LinkedList<>();
//            List<EmdAddressDepth> depths = emdAddressDepthRepository.findByEmdAddress1AndDepth(e, 1);
//            for(EmdAddressDepth emdAddressDepth : depths) {
//                queue.add(emdAddressDepth.getEmdAddress2());
//            }
//            while(!queue.isEmpty()) {
//                EmdAddress now = queue.poll();
//                List<EmdAddressDepth> temp = emdAddressDepthRepository.findByEmdAddress1AndDepth(now, 1);
//                for(EmdAddressDepth ed : temp) {
//                    if (emdAddressDepthRepository.existsByEmdAddress1AndEmdAddress2(e, ed.getEmdAddress2())) continue;
//                    emdAddressDepthRepository.save(EmdAddressDepth.builder()
//                            .emdAddress1(e)
//                            .emdAddress2(ed.getEmdAddress2())
//                            .depth(2)
//                            .build());
//                }
//            }
//
//        }
        return null;
    }

    @Override
    public List<EmdAddress> data() {
        List<EmdAddress> list = emdAddressRepository.findAll();
        for(EmdAddress e : list) {
            Queue<EmdAddress> queue = new LinkedList<>();
            List<EmdAddressDepth> depths = emdAddressDepthRepository.findByEmdAddress1AndDepth(e.getId(), 2);

            for(EmdAddressDepth emdAddressDepth : depths) {
                queue.add(emdAddressDepth.getEmdAddress2());
            }
            while(!queue.isEmpty()) {
                EmdAddress now = queue.poll();
                List<EmdAddressDepth> temp = emdAddressDepthRepository.findByEmdAddress1AndDepth(now.getId(), 1);
                for(EmdAddressDepth ed : temp) {
                    if (e.getId() == ed.getEmdAddress2().getId() || emdAddressDepthRepository.existsByEmdAddress1AndEmdAddress2(e, ed.getEmdAddress2())) continue;
                    emdAddressDepthRepository.save(EmdAddressDepth.builder()
                            .emdAddress1(e)
                            .emdAddress2(ed.getEmdAddress2())
                            .depth(3)
                            .build());
                }
            }
        }
        return null;

    }
}
