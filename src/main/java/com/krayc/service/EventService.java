package com.krayc.service;

import com.krayc.model.EventEntity;
import com.krayc.model.EventSeatEntity;
import com.krayc.model.EventTypeEntity;
import com.krayc.model.SeatEntity;

import java.util.Collection;
import java.util.List;

public interface EventService {

    public EventTypeEntity findTypeByTid(Integer tid);

    public List<EventTypeEntity> findAllEventTypes();

    public List<EventEntity> findAllEvents();

    public EventEntity findByEid(Integer eid);

    public void addEvent(EventEntity eventEntity);

    public void addEventSeat(EventSeatEntity eventSeatEntity);

    public List<EventSeatEntity> findEventSeatOtherThanSeatsAndInEvent(Collection<SeatEntity> eventSeatEntities, EventEntity eventEntity);

}
