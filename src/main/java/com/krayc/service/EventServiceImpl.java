package com.krayc.service;

import com.krayc.model.EventEntity;
import com.krayc.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<EventEntity> findAllEvents() {
        return eventRepository.findAll();
    }

}
