package com.krayc.repository;

import com.krayc.model.EventEntity;
import com.krayc.model.EventSeatEntity;
import com.krayc.model.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface EventSeatRepository extends JpaRepository<EventSeatEntity, Integer> {

    public List<EventSeatEntity> findEventSeatEntitiesBySeatInAndEventIs(Collection<SeatEntity> seatEntities, EventEntity eventEntity);

}
