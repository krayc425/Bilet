package com.krayc.repository;

import com.krayc.model.AdminBookEntity;
import com.krayc.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AdminBookRepository extends JpaRepository<AdminBookEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update AdminBookEntity o set o.isConfirmed=:isConfirmed where o.event=:eventEntity")
    public void confirmEvent(@Param("isConfirmed") Byte isConfirmed, @Param("eventEntity") EventEntity eventEntity);

    @Modifying
    @Transactional
    @Query("update AdminBookEntity o set o.amount=:amount where o.event=:eventEntity")
    public void updateAmount(@Param("amount") Double amount, @Param("eventEntity") EventEntity eventEntity);

    public AdminBookEntity findByEvent(EventEntity eventEntity);

}
