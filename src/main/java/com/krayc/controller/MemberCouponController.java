package com.krayc.controller;

import com.krayc.model.MemberCouponEntity;
import com.krayc.model.MemberEntity;
import com.krayc.service.CouponService;
import com.krayc.service.MemberService;
import com.krayc.vo.MemberCouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
@RequestMapping(value = "member/coupon")
public class MemberCouponController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponService couponService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String coupon(@PathVariable("id") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        Collection<MemberCouponVO> memberCouponEntities = couponService.findMemberCouponVOsByMember(memberEntity);
        modelMap.addAttribute("member", memberEntity);
        modelMap.addAttribute("memberCoupons", memberCouponEntities);
        return "member/coupon/memberCouponDetail";
    }

    @RequestMapping(value = "{id}/redeem", method = RequestMethod.GET)
    public String redeemCoupon(@PathVariable("id") Integer mid, ModelMap modelMap) {
        modelMap.addAttribute("allCoupons", couponService.findAllCoupons());
        MemberEntity memberEntity = memberService.findByMid(mid);
        modelMap.addAttribute("member", memberEntity);
        return "member/coupon/redeemMemberCoupon";
    }

    @RequestMapping(value = "{id}/redeem/{cid}", method = RequestMethod.GET)
    public String redeemCouponPost(@PathVariable("id") Integer mid, @PathVariable("cid") Integer cid) {
        MemberCouponEntity memberCouponEntity = new MemberCouponEntity();
        memberCouponEntity.setCouponByCid(couponService.findByCid(cid));
        memberCouponEntity.setMemberByMid(memberService.findByMid(mid));
        memberCouponEntity.setUsage(0);
        couponService.redeemCoupon(memberCouponEntity);
        return "redirect:/member/coupon/" + mid + "/redeem";
    }

}
