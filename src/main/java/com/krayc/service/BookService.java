package com.krayc.service;

import com.krayc.model.EventEntity;
import com.krayc.model.MemberBookEntity;
import com.krayc.model.MemberEntity;

public interface BookService {

    public void createMemberBookEntity(MemberBookEntity memberBookEntity);

    public Double totalMemberBookAmount(MemberEntity memberEntity);

    public void createEventBookEntity(EventEntity eventEntity);

    public void confirmEvent(EventEntity eventEntity);

    public void updateEventAmount(EventEntity eventEntity, Double originalAmount);

}
