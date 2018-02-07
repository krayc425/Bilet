package com.krayc.service;

import com.krayc.model.CouponEntity;
import com.krayc.model.MemberCouponEntity;
import com.krayc.model.MemberEntity;
import com.krayc.vo.MemberCouponVO;

import java.util.List;

public interface CouponService {

    public List<CouponEntity> findAllCoupons();

    public void redeemCoupon(MemberCouponEntity memberCouponEntity);

    public CouponEntity findByCid(Integer cid);

    public MemberCouponEntity findByMcid(Integer mcid);

    public List<MemberCouponEntity> findAvailableCouponsByMember(MemberEntity memberEntity);

    public List<MemberCouponVO> findMemberCouponVOsByMember(MemberEntity memberEntity);

}
