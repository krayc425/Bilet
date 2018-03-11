package com.krayc.repository;

import com.krayc.model.EventEntity;
import com.krayc.model.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Integer> {

    public List<EventEntity> findByTimeAfter(Timestamp timeStamp);

    public List<EventEntity> findByVenueId(VenueEntity venueEntity);

}
