package com.krayc.service.impl;

import com.krayc.model.*;
import com.krayc.repository.AdminBookRepository;
import com.krayc.repository.MemberBookRepository;
import com.krayc.repository.VenueBookRepository;
import com.krayc.service.BookService;
import com.krayc.util.PayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private MemberBookRepository memberBookRepository;

    @Autowired
    private AdminBookRepository adminBookRepository;

    @Autowired
    private VenueBookRepository venueBookRepository;

    public void createMemberBookEntity(MemberBookEntity memberBookEntity) {
        memberBookRepository.saveAndFlush(memberBookEntity);
    }

    public Double totalMemberBookAmount(MemberEntity memberEntity) {
        Double total = 0.0;
        for (MemberBookEntity memberBookEntity : memberBookRepository.findByMemberIsAndTypeIsNot(memberEntity, PayType.ONLINE_CHARGE)) {
            total += memberBookEntity.getAmount();
        }
        return -total;
    }

    public void createEventBookEntity(EventEntity eventEntity) {
        AdminBookEntity adminBookEntity = new AdminBookEntity();
        adminBookEntity.setAmount(0);
        adminBookEntity.setEvent(eventEntity);
        adminBookEntity.setVenue(eventEntity.getVenueId());

        VenueBookEntity venueBookEntity = new VenueBookEntity();
        venueBookEntity.setAmount(0);
        venueBookEntity.setEvent(eventEntity);
        venueBookEntity.setVenue(eventEntity.getVenueId());

        adminBookRepository.saveAndFlush(adminBookEntity);
        venueBookRepository.saveAndFlush(venueBookEntity);
    }

    public void confirmEvent(EventEntity eventEntity) {
        adminBookRepository.confirmEvent(Byte.valueOf("1"), eventEntity);
        venueBookRepository.confirmEvent(Byte.valueOf("1"), eventEntity);
    }

    public void updateEventAmount(EventEntity eventEntity, Double originalAmount) {
        Double adminAmount = adminBookRepository.findByEvent(eventEntity).getAmount();
        adminBookRepository.updateAmount(adminAmount + originalAmount * 0.2, eventEntity);

        Double venueAmount = venueBookRepository.findByEvent(eventEntity).getAmount();
        venueBookRepository.updateAmount(venueAmount + originalAmount * 0.8, eventEntity);
    }

}
