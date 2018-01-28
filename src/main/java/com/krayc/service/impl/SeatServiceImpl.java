package com.krayc.service.impl;

import com.krayc.model.SeatEntity;
import com.krayc.repository.SeatRepository;
import com.krayc.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public SeatEntity findBySid(Integer sid) {
        return seatRepository.findOne(sid);
    }

    public void deleteSeat(Integer sid) {
        seatRepository.delete(sid);
        seatRepository.flush();
    }

    public void updateSeat(SeatEntity seatEntity) {
        seatRepository.updateSeat(seatEntity.getName(), seatEntity.getNumber(), seatEntity.getSid());
        seatRepository.flush();
    }

    public void addSeat(SeatEntity seatEntity) {
        seatRepository.saveAndFlush(seatEntity);

    }
}
