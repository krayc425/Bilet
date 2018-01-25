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
    @Query("update MemberEntity us set us.email=:qEmail, us.password=:qPassword where us.mid=:qId")
    public void updateUser(@Param("qEmail") String qEmail, @Param("qPassword") String password, @Param("qId") Integer qId);

}
