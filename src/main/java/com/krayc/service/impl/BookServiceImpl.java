package com.krayc.service.impl;

import com.krayc.model.*;
import com.krayc.repository.AdminBookRepository;
import com.krayc.repository.MemberBookRepository;
import com.krayc.repository.VenueBookRepository;
import com.krayc.service.BookService;
import com.krayc.util.OrderType;
import com.krayc.util.PayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void updateEventAmount(OrderEntity orderEntity, Double originalAmount) {
        EventEntity eventEntity = orderEntity.getEventByEid();

        // 现场购票，全额进入场地分成
        // 网上购票，80% 进入场地分成，20% 进入网站分成

        Double venueAmount = venueBookRepository.findByEvent(eventEntity).getAmount();
        if (orderEntity.getType() == OrderType.AT_VENUE) {
            venueBookRepository.updateAmount(venueAmount + originalAmount, eventEntity);
        } else {
            Double adminAmount = adminBookRepository.findByEvent(eventEntity).getAmount();
            adminBookRepository.updateAmount(adminAmount + originalAmount * 0.2, eventEntity);
            venueBookRepository.updateAmount(venueAmount + originalAmount * 0.8, eventEntity);
        }
    }

    public List<AdminBookEntity> getAllAdminBooks() {
        return adminBookRepository.findAll();
    }

    public List<VenueBookEntity> findVenueBooksByVenue(VenueEntity venueEntity) {
        return venueBookRepository.findByVenue(venueEntity);
    }

    public List<MemberBookEntity> findMemberBooksByMember(MemberEntity memberEntity) {
        return memberBookRepository.findByMember(memberEntity);
    }

}
