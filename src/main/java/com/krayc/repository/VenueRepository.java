package com.krayc.repository;

import com.krayc.model.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VenueRepository extends JpaRepository<VenueEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update VenueEntity ve set ve.name=:qName, ve.password=:qPassword, ve.address=:qAddress where ve.vid=:qId")
    public void updateVenue(@Param("qName") String qName, @Param("qPassword") String qPassword, @Param("qAddress") String qAddress, @Param("qId") Integer qId);

    public List<VenueEntity> findByIsPassedIs(Byte isPassed);

    @Modifying
    @Transactional
    @Query("update VenueEntity ve set ve.isPassed=:qIsPassed where ve.vid=:qId")
    public void passVenue(@Param("qIsPassed") Byte isPassed, @Param("qId") Integer qId);

}
