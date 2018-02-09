package com.krayc.repository;

import com.krayc.model.MemberCouponEntity;
import com.krayc.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update MemberCouponEntity c set c.usage=:qUsage where c.mcid=:qMcid")
    public void updateUsage(@Param("qUsage") Integer qUsage, @Param("qMcid") int qMcid);

    public List<MemberCouponEntity> findByMemberByMidAndUsage(MemberEntity memberEntity, Integer usage);

}
