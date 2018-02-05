package com.krayc.repository;

import com.krayc.model.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<CouponEntity, Integer> {
}
