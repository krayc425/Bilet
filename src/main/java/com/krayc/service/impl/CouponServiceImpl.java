package com.krayc.service.impl;

import com.krayc.model.CouponEntity;
import com.krayc.model.MemberCouponEntity;
import com.krayc.model.MemberEntity;
import com.krayc.repository.CouponRepository;
import com.krayc.repository.MemberCouponRepository;
import com.krayc.repository.MemberRepository;
import com.krayc.service.CouponService;
import com.krayc.vo.MemberCouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
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

    public void redeemCoupon(MemberCouponEntity memberCouponEntity) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(date);
        memberCouponEntity.setTime(dateString);
        memberCouponRepository.saveAndFlush(memberCouponEntity);

        // 扣除会员积分
        MemberEntity memberEntity = memberCouponEntity.getMemberByMid();
        memberRepository.updateCurrentPoint(memberEntity.getCurrentPoint() - memberCouponEntity.getCouponByCid().getPoint(), memberEntity.getMid());
    }

    public CouponEntity findByCid(Integer cid) {
        return couponRepository.findOne(cid);
    }


    public List<MemberCouponVO> findMemberCouponVOsByMid(Integer mid) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ArrayList<MemberCouponVO> memberCouponVOS = new ArrayList<MemberCouponVO>();
        for (MemberCouponEntity memberCouponEntity : memberRepository.findOne(mid).getMemberCoupons()) {

            String usageDescription = "";
            switch (memberCouponEntity.getUsage()) {
                case 0:
                    usageDescription = "兑换";
                    break;
                case 1:
                    usageDescription = "试用";
                    break;
                default:
                    usageDescription = "";
                    break;
            }

            memberCouponVOS.add(new MemberCouponVO(simpleDateFormat.format(memberCouponEntity.getTime()),
                    usageDescription, memberCouponEntity.getCouponByCid().getName()));
        }
        return memberCouponVOS;
    }
}
