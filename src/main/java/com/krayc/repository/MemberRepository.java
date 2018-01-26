package com.krayc.repository;

import com.krayc.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update MemberEntity us set us.email=:qEmail, us.password=:qPassword where us.mid=:qId")
    public void updateMember(@Param("qEmail") String qEmail, @Param("qPassword") String password, @Param("qId") Integer qId);

    public MemberEntity findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update MemberEntity us set us.isEmailPassed=:isActivated where us.mid=:mId")
    public void updateActivationMember(@Param("mId") Integer mid, @Param("isActivated") Byte isActivated);

}
