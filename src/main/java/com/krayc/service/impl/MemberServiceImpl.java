package com.krayc.service.impl;

import com.krayc.model.BankAccountEntity;
import com.krayc.model.MemberBookEntity;
import com.krayc.model.MemberEntity;
import com.krayc.repository.BankAccountRepository;
import com.krayc.repository.MemberRepository;
import com.krayc.service.BookService;
import com.krayc.service.MemberService;
import com.krayc.util.LoginStatus;
import com.krayc.util.PayType;
import io.github.biezhi.ome.OhMyEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BookService bookService;

    public void updateMember(MemberEntity user) {
        BankAccountEntity oldBankAccount = bankAccountRepository.findByBankAccount(memberRepository.findByMid(user.getMid()).getBankAccount());

        memberRepository.updateMember(user.getPassword(), user.getBankAccount(), user.getMid());

        bankAccountRepository.updateAccount(user.getBankAccount(), oldBankAccount.getBid());

        memberRepository.flush();
    }

    public void terminateMember(Integer mid) {
        memberRepository.terminateMember(mid, Byte.valueOf("1"));
    }

    public void activateMember(Integer mid) {
        memberRepository.updateActivationMember(mid, Byte.valueOf("1"));
    }

    public double findBalance(String bankAccount) {
        return bankAccountRepository.findByBankAccount(bankAccount).getBalance();
    }

    public BankAccountEntity findBankAccountEntity(String bankAccount) {
        return bankAccountRepository.findByBankAccount(bankAccount);
    }

    public MemberEntity findByMid(Integer mid) {
        return memberRepository.findOne(mid);
    }

    public MemberEntity findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public LoginStatus login(MemberEntity memberEntity) {
        MemberEntity anotherEntity = memberRepository.findByEmail(memberEntity.getEmail());
        if (anotherEntity == null) {
            return LoginStatus.LOGIN_WRONG_EMAIL_PASSWORD;
        }
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

        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setBalance(0);
        bankAccountEntity.setBankAccount(memberEntity.getBankAccount());
        bankAccountRepository.saveAndFlush(bankAccountEntity);

        System.out.println("Send Email!!!!");

        OhMyEmail.config(OhMyEmail.SMTP_QQ(true), "krayc@foxmail.com", "lkpvwjjcrlhjbajj");

//        OhMyEmail.config(OhMyEmail.SMTP_163(true), "krayc425@163.com", "songkuixi2");
        try {
            OhMyEmail.subject("Welcome to Bilet!")
                    .from("Bilet")
                    .cc("krayc@foxmail.com")
                    .to(memberEntity.getEmail())
                    .html("" +
                            "<h1>欢迎加入 Bilet!</h1>" +
                            "<p>请点击链接确认注册: http://localhost:8080/member/activate/" + memberEntity.getMid() + "\n" +
                            "<p>感谢您对 Bilet 的支持！</p>")
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chargeAmount(MemberEntity memberEntity, Double amount) {
        BankAccountEntity bankAccountEntity = bankAccountRepository.findByBankAccount(memberEntity.getBankAccount());
        bankAccountRepository.updateBalance(bankAccountEntity.getBalance() + amount,
                bankAccountEntity.getBankAccount());

        MemberBookEntity memberBookEntity = new MemberBookEntity();
        memberBookEntity.setMember(memberEntity);
        memberBookEntity.setType(PayType.ONLINE_CHARGE);
        memberBookEntity.setAmount(amount);
        memberBookEntity.setTime(new Timestamp(System.currentTimeMillis()));

        bookService.createMemberBookEntity(memberBookEntity);
    }

    public List<MemberEntity> findAllMembers() {
        return memberRepository.findAll();
    }

}
