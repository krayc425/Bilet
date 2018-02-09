package com.krayc.service;

import com.krayc.model.MemberBookEntity;
import com.krayc.model.MemberEntity;

public interface BookService {

    public void createMemberBookEntity(MemberBookEntity memberBookEntity);

    public Double totalMemberBookAmount(MemberEntity memberEntity);

}
