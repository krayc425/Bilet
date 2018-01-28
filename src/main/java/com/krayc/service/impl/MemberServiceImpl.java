package com.krayc.service.impl;

import com.krayc.model.MemberEntity;
import com.krayc.repository.MemberRepository;
import com.krayc.service.MemberService;
import com.krayc.util.LoginStatus;
import io.github.biezhi.ome.OhMyEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public void updateMember(MemberEntity user) {
        memberRepository.updateMember(user.getPassword(), user.getBankAccount(), user.getMid());
        memberRepository.flush();
    }

    public void terminateMember(Integer mid) {
        memberRepository.terminateMember(mid, Byte.valueOf("1"));
    }

    public void activateMember(Integer mid) {
        memberRepository.updateActivationMember(mid, Byte.valueOf("1"));
    }

    public MemberEntity findByMid(Integer mid) {
        return memberRepository.findOne(mid);
    }

    public MemberEntity findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public LoginStatus login(MemberEntity memberEntity) {
        MemberEntity anotherEntity = memberRepository.findByEmail(memberEntity.getEmail());
        if (!anotherEntity.getPassword().equals(memberEntity.getPassword())) {
            return LoginStatus.LOGIN_WRONG_EMAIL_PASSWORD;
        } else {
            if (anotherEntity.getIsEmailPassed() == Byte.valueOf("0")) {
                return LoginStatus.LOGIN_NOT_ACTIVATED;
            } else if (anotherEntity.getIsTerminated() == Byte.valueOf("1")) {
                return LoginStatus.LOGIN_TERMINATED;
            } else {
                return LoginStatus.LOGIN_SUCCESS;
            }
        }
    }

    public void addMember(MemberEntity memberEntity) {
        memberRepository.saveAndFlush(memberEntity);
        OhMyEmail.config(OhMyEmail.SMTP_163(false), "krayc425@163.com", "songkuixi2");
        try {
            OhMyEmail.subject("Welcome to Bilet!")
                    .from("Bilet")
                    .to(memberEntity.getEmail())
                    .text("http://localhost:8080/member/activate/" + memberEntity.getMid())
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
