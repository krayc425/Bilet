package com.krayc.service;

import com.krayc.model.*;

import java.util.Collection;
import java.util.List;

public interface EventService {

    public EventTypeEntity findTypeByTid(Integer tid);

    public List<EventTypeEntity> findAllEventTypes();

    public List<EventEntity> findAllEvents();

    public List<EventEntity> findAvailableEvents();

    public EventEntity findByEid(Integer eid);

    public void addEvent(EventEntity eventEntity);

    public void addEventSeat(EventSeatEntity eventSeatEntity);

    public List<EventSeatEntity> findEventSeatOtherThanSeatsAndInEvent(Collection<SeatEntity> eventSeatEntities, EventEntity eventEntity);

    public Integer unavailableSeatNumberByEvent(EventSeatEntity eventSeatEntity);

    public List<EventSeatEntity> findEventSeatsByEid(EventEntity eventEntity);

    public List<EventEntity> findByVenueEntity(VenueEntity venueEntity);

}
