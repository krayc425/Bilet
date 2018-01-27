package com.krayc.repository;

import com.krayc.model.EventTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventTypeEntity, Integer> {

}
