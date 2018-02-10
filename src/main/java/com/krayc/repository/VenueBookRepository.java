package com.krayc.repository;

import com.krayc.model.EventEntity;
import com.krayc.model.VenueBookEntity;
import com.krayc.model.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VenueBookRepository extends JpaRepository<VenueBookEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update VenueBookEntity o set o.isConfirmed=:isConfirmed where o.event=:eventEntity")
    public void confirmEvent(@Param("isConfirmed") Byte isConfirmed, @Param("eventEntity") EventEntity eventEntity);

    @Modifying
    @Transactional
    @Query("update VenueBookEntity o set o.amount=:amount where o.event=:eventEntity")
    public void updateAmount(@Param("amount") Double amount, @Param("eventEntity") EventEntity eventEntity);

    public VenueBookEntity findByEvent(EventEntity eventEntity);

    public List<VenueBookEntity> findByVenue(VenueEntity venueEntity);

}