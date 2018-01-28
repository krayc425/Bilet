package com.krayc.service;

import com.krayc.model.EventEntity;

import java.util.List;

public interface EventService {

    public List<EventEntity> findAllEvents();

}
