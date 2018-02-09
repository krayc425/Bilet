package com.krayc.service.impl;

import com.krayc.model.VenueEntity;
import com.krayc.repository.VenueRepository;
import com.krayc.service.VenueService;
import com.krayc.util.LoginStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public void passVenueOrNot(Integer vid, Boolean isPass) {
        venueRepository.passVenue(Byte.valueOf(isPass ? "1" : "-1"), vid);
        venueRepository.flush();
    }

    public List<VenueEntity> findToBePassedVenues() {
        return venueRepository.findByIsPassedIs(Byte.valueOf("0"));
    }

    public VenueEntity findByVid(Integer vid) {
        return venueRepository.findOne(vid);
    }

    public void addVenue(VenueEntity venueEntity) {
        venueRepository.saveAndFlush(venueEntity);
    }

    public void updateVenue(VenueEntity venueEntity) {
        venueRepository.updateVenue(venueEntity.getName(), venueEntity.getPassword(), venueEntity.getAddress(), venueEntity.getVid());
        venueRepository.passVenue(Byte.valueOf("0"), venueEntity.getVid());
        venueRepository.flush();
    }

    public LoginStatus login(VenueEntity venueEntity) {
        VenueEntity anotherEntity = findByVid(venueEntity.getVid());
        if (anotherEntity == null) {
            return LoginStatus.LOGIN_WRONG_EMAIL_PASSWORD;
        }
        if (anotherEntity.getPassword().equals(venueEntity.getPassword())) {
            return LoginStatus.LOGIN_SUCCESS;
        } else {
            return LoginStatus.LOGIN_WRONG_EMAIL_PASSWORD;
        }
    }

}
