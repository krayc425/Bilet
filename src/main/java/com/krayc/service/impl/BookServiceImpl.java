package com.krayc.service.impl;

import com.krayc.model.MemberBookEntity;
import com.krayc.model.MemberEntity;
import com.krayc.repository.MemberBookRepository;
import com.krayc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private MemberBookRepository memberBookRepository;

    public void createMemberBookEntity(MemberBookEntity memberBookEntity) {
        memberBookRepository.saveAndFlush(memberBookEntity);
    }

    public Double totalMemberBookAmount(MemberEntity memberEntity) {
        Double total = 0.0;
        for (MemberBookEntity memberBookEntity : memberBookRepository.findByMemberIsAndTypeIsNot(memberEntity, Byte.valueOf("3"))) {
            total += memberBookEntity.getAmount();
        }
        return -total;
    }

}
