package com.krayc.repository;

import com.krayc.model.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SeatRepository extends JpaRepository<SeatEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update SeatEntity seat set seat.name=:qName, seat.number=:qNumber where seat.sid=:qSid")
    public void updateSeat(@Param("qName") String name, @Param("qNumber") int number, @Param("qSid") int id);

}
