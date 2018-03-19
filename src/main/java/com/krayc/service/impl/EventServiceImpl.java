package com.krayc.service.impl;

import com.krayc.model.*;
import com.krayc.repository.*;
import com.krayc.service.BookService;
import com.krayc.service.EventService;
import com.krayc.service.OrderService;
import com.krayc.util.OrderStatus;
import com.krayc.util.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventSeatRepository eventSeatRepository;

    @Autowired
    private OrderEventSeatRepository orderEventSeatRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    public EventTypeEntity findTypeByTid(Integer tid) {
        return eventTypeRepository.findOne(tid);
    }

    public List<EventTypeEntity> findAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    public List<EventEntity> findAllEvents() {
        return eventRepository.findAll();
    }

    public List<EventEntity> findAvailableEvents() {
        return eventRepository.findByTimeAfter(new Timestamp(System.currentTimeMillis()));
    }

    public EventEntity findByEid(Integer eid) {
        return eventRepository.findOne(eid);
    }

    public void addEvent(EventEntity eventEntity) {
        eventRepository.saveAndFlush(eventEntity);

        bookService.createEventBookEntity(eventEntity);

        Timer timer = new Timer();
        // 两周前，配票
        timer.schedule(new OrderDistributor(eventEntity), new Timestamp(eventEntity.getTime().getTime() - 14 * 24 * 60 * 60 * 1000));
    }

    public Integer unavailableSeatNumberByEvent(EventSeatEntity eventSeatEntity) {
        return orderEventSeatRepository.findByEventSeatByEsidAndIsValidIsNot(eventSeatEntity, 1).size();
    }

    public void addEventSeat(EventSeatEntity eventSeatEntity) {
        eventSeatRepository.saveAndFlush(eventSeatEntity);
    }

    public List<EventSeatEntity> findEventSeatOtherThanSeatsAndInEvent(Collection<SeatEntity> eventSeatEntities, EventEntity eventEntity) {
        if (eventSeatEntities.size() == 0) {
            return new ArrayList<EventSeatEntity>();
        }
        return eventSeatRepository.findEventSeatEntitiesBySeatInAndEventIs(eventSeatEntities, eventEntity);
    }

    public List<EventSeatEntity> findEventSeatsByEid(EventEntity eventEntity) {
        return eventSeatRepository.findByEvent(eventEntity);
    }

    public List<EventEntity> findByVenueEntity(VenueEntity venueEntity) {
        return eventRepository.findByVenueId(venueEntity);
    }

    public class OrderDistributor extends TimerTask {

        EventEntity eventEntity;

        OrderDistributor(EventEntity eventEntity) {
            this.eventEntity = eventEntity;
        }

        @Override
        public void run() {
            for (OrderEntity orderEntity : orderRepository.findByEventByEid(eventEntity)) {
                if (orderEntity.getType() == OrderType.RANDOM_SEAT && orderEntity.getStatus() == OrderStatus.ORDER_WAITING) {
                    orderService.distributeOrderEventSeat(orderEntity);
                }
            }
        }

    }
}
