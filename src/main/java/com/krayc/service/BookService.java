package com.krayc.service;

import com.krayc.model.*;

import java.util.List;

public interface BookService {

    public void createMemberBookEntity(MemberBookEntity memberBookEntity);

    public Double totalMemberBookAmount(MemberEntity memberEntity);

    public void createEventBookEntity(EventEntity eventEntity);

    public void confirmEvent(EventEntity eventEntity);

    public void updateEventAmount(OrderEntity orderEntity, Double originalAmount);

    public List<AdminBookEntity> getAllAdminBooks();

    public List<VenueBookEntity> findVenueBooksByVenue(VenueEntity venueEntity);

    public List<MemberBookEntity> findMemberBooksByMember(MemberEntity memberEntity);

}
