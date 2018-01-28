package com.krayc.service;

import com.krayc.model.VenueEntity;
import com.krayc.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public void passVenueOrNot(Integer vid, Boolean isPass) {
        venueRepository.passVenue(Byte.valueOf(isPass ? "1" : "0"), vid);
        venueRepository.flush();
    }


    public List<VenueEntity> findToBePassedVenues(){
        return venueRepository.findByIsPassedIs(Byte.valueOf("0"));
    }
}
