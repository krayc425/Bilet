package com.krayc.service.impl;

import com.krayc.model.CouponEntity;
import com.krayc.model.MemberCouponEntity;
import com.krayc.model.MemberEntity;
import com.krayc.repository.CouponRepository;
import com.krayc.repository.MemberCouponRepository;
import com.krayc.repository.MemberRepository;
import com.krayc.service.CouponService;
import com.krayc.util.DateFormatter;
import com.krayc.vo.MemberCouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<CouponEntity> findAllCoupons() {
        return couponRepository.findAll();
    }

    public MemberCouponEntity findByMcid(Integer mcid) {
        return memberCouponRepository.findOne(mcid);
    }

    public List<MemberCouponEntity> findAvailableCouponsByMember(MemberEntity memberEntity) {
        return memberCouponRepository.findByMemberByMidAndUsage(memberEntity, 0);
    }

    public void redeemCoupon(MemberCouponEntity memberCouponEntity) {
        Date date = new Date(System.currentTimeMillis());
        memberCouponEntity.setTime(DateFormatter.getDateFormatter().stringFromDate(date));
        memberCouponRepository.saveAndFlush(memberCouponEntity);

        // 扣除会员积分
        MemberEntity memberEntity = memberCouponEntity.getMemberByMid();
        memberRepository.updateCurrentPoint(memberEntity.getCurrentPoint() - memberCouponEntity.getCouponByCid().getPoint(), memberEntity.getMid());
    }

    public CouponEntity findByCid(Integer cid) {
        return couponRepository.findOne(cid);
    }

    public List<MemberCouponVO> findMemberCouponVOsByMember(MemberEntity memberEntity) {
        ArrayList<MemberCouponVO> memberCouponVOS = new ArrayList<MemberCouponVO>();
        for (MemberCouponEntity memberCouponEntity : memberEntity.getMemberCoupons()) {
            memberCouponVOS.add(new MemberCouponVO(memberCouponEntity));
        }
        return memberCouponVOS;
    }

}
