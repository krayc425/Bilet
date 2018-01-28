package com.krayc.service;

import com.krayc.model.MemberEntity;
import com.krayc.util.LoginStatus;

public interface MemberService {

    public void updateMember(MemberEntity memberEntity);

    public void terminateMember(Integer mid);

    public void activateMember(Integer mid);

    public MemberEntity findByMid(Integer mid);

    public MemberEntity findByEmail(String email);

    public LoginStatus login(MemberEntity memberEntity);

    public void addMember(MemberEntity memberEntity);

}
