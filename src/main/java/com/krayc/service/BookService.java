package com.krayc.service;

import com.krayc.model.AdminBookEntity;
import com.krayc.model.EventEntity;
import com.krayc.model.MemberBookEntity;
import com.krayc.model.MemberEntity;

import java.util.List;

public interface BookService {

    public void createMemberBookEntity(MemberBookEntity memberBookEntity);

    public Double totalMemberBookAmount(MemberEntity memberEntity);

    public void createEventBookEntity(EventEntity eventEntity);

    public void confirmEvent(EventEntity eventEntity);

    public void updateEventAmount(EventEntity eventEntity, Double originalAmount);

    public List<AdminBookEntity> getAllAdminBooks();

}
