package com.krayc.repository;

import com.krayc.model.MemberCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Integer> {
}
