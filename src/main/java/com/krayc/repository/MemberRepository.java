package com.krayc.repository;

import com.krayc.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update MemberEntity us set us.bankAccount=:qBankAccount, us.password=:qPassword where us.mid=:qId")
    public void updateMember(@Param("qPassword") String password, @Param("qBankAccount") String bankAccount, @Param("qId") Integer qId);

    public MemberEntity findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update MemberEntity us set us.isEmailPassed=:isActivated where us.mid=:mId")
    public void updateActivationMember(@Param("mId") Integer mid, @Param("isActivated") Byte isActivated);

    @Modifying
    @Transactional
    @Query("update MemberEntity us set us.isTerminated=:isTerminated where us.mid=:mid")
    public void terminateMember(@Param("mid") Integer mid, @Param("isTerminated") Byte isTerminated);

    @Modifying
    @Transactional
    @Query("update MemberEntity us set us.currentPoint=:point where us.mid=:qId")
    public void updateCurrentPoint(@Param("point") Integer point, @Param("qId") Integer qId);

}
