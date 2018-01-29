package com.krayc.service;

import com.krayc.model.VenueEntity;
import com.krayc.util.LoginStatus;

import java.util.List;

public interface VenueService {

    public void passVenueOrNot(Integer vid, Boolean isPass);

    public List<VenueEntity> findToBePassedVenues();

    public VenueEntity findByVid(Integer vid);

    public void addVenue(VenueEntity venueEntity);

    public void updateVenue(VenueEntity venueEntity);

    public LoginStatus login(VenueEntity venueEntity);

}
