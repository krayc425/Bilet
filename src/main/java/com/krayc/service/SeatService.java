package com.krayc.service;

import com.krayc.model.SeatEntity;

public interface SeatService {

    public SeatEntity findBySid(Integer sid);

    public void deleteSeat(Integer sid);

    public void updateSeat(SeatEntity seatEntity);

    public void addSeat(SeatEntity seatEntity);

}
