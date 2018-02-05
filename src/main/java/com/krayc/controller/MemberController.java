package com.krayc.controller;

import com.krayc.model.MemberCouponEntity;
import com.krayc.model.MemberEntity;
import com.krayc.service.CouponService;
import com.krayc.service.EventService;
import com.krayc.service.LevelService;
import com.krayc.service.MemberService;
import com.krayc.vo.MemberCouponVO;
import com.krayc.vo.MemberInfoVO;
import com.krayc.vo.MemberUpdateVO;
import com.krayc.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

@Controller
@RequestMapping(value = "member")
public class MemberController extends BaseController {

    @Autowired
    private EventService eventService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponService couponService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addMember() {
        return "member/addMember";
    }

    @RequestMapping(value = "addPost", method = RequestMethod.POST)
    public String addMemberPost(@ModelAttribute("member") MemberEntity memberEntity) {
        memberService.addMember(memberEntity);
        return "redirect:/";
    }

    @RequestMapping(value = "show/{id}", method = RequestMethod.GET)
    public String showMember(@PathVariable("id") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity1 = memberService.findByMid(mid);
        MemberInfoVO memberInfoVO = new MemberInfoVO(
                memberEntity1.getMid(),
                memberEntity1.getEmail(),
                memberEntity1.getBankAccount(),
                levelService.findLevelEntityWithPoint(memberEntity1.getTotalPoint()).getDescription(),
                memberEntity1.getTotalPoint(),
                memberEntity1.getCurrentPoint(),
                memberEntity1.getIsTerminated() == Byte.valueOf("1") ? "已被注销" : "可以使用",
                memberEntity1.getIsEmailPassed() == Byte.valueOf("1") ? "已经激活" : "尚未激活");
        modelMap.addAttribute("member", memberInfoVO);

        modelMap.addAttribute("events", eventService.findAllEvents());

        return "member/memberDetail";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView loginMember() {
        return new ModelAndView("member/loginMember");
    }

    @RequestMapping(value = "loginPost", method = RequestMethod.POST)
    public ModelAndView loginMemberPost(@ModelAttribute("member") MemberEntity memberEntity, HttpServletRequest request) {
        switch (memberService.login(memberEntity)) {
            case LOGIN_SUCCESS:
                MemberEntity memberEntity1 = memberService.findByEmail(memberEntity.getEmail());

                HttpSession session = request.getSession(false);
                session.setAttribute("member", memberEntity1);

                return new ModelAndView("redirect:/member/show/" + memberEntity1.getMid());
            case LOGIN_NOT_ACTIVATED: {
                MessageVO messageVO = new MessageVO(false, "账户尚未被激活");
                return this.handleMessage(messageVO, "member/loginMember");
            }
            case LOGIN_WRONG_EMAIL_PASSWORD: {
                MessageVO messageVO = new MessageVO(false, "邮箱或密码错误");
                return this.handleMessage(messageVO, "member/loginMember");
            }
            case LOGIN_TERMINATED: {
                MessageVO messageVO = new MessageVO(false, "账户已经被取消");
                return this.handleMessage(messageVO, "member/loginMember");
            }
            default:
                return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logoutMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("member", null);
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "activate/{id}", method = RequestMethod.GET)
    public String activateMember(@PathVariable("id") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        if (memberEntity.getIsEmailPassed() == 1) {
            System.out.println("激活过了");
        } else {
            memberService.activateMember(mid);
        }
        return "redirect:/member/show/" + mid;
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateUser(@PathVariable("id") Integer userId, ModelMap modelMap) {
        MemberEntity memberEntity = memberService.findByMid(userId);
        MemberUpdateVO memberUpdateVO = new MemberUpdateVO(memberEntity.getMid(), memberEntity.getBankAccount(), memberEntity.getPassword());

        modelMap.addAttribute("member", memberUpdateVO);
        return "member/updateMember";
    }

    @RequestMapping(value = "updatePost/{id}", method = RequestMethod.POST)
    public String updateUserPost(@PathVariable("id") Integer userId, @ModelAttribute("memberP") MemberEntity user) {
        user.setMid(userId);
        memberService.updateMember(user);
        return "redirect:/member/show/" + userId;
    }

    @RequestMapping(value = "terminate/{id}", method = RequestMethod.GET)
    public String terminateUser(@PathVariable("id") Integer mid) {
        memberService.terminateMember(mid);
        return "redirect:/";
    }

    @RequestMapping(value = "coupon/{id}", method = RequestMethod.GET)
    public String coupon(@PathVariable("id") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        Collection<MemberCouponVO> memberCouponEntities = couponService.findMemberCouponVOsByMid(mid);
        modelMap.addAttribute("member", memberEntity);
        modelMap.addAttribute("memberCoupons", memberCouponEntities);
        return "member/coupon/memberCouponDetail";
    }

    @RequestMapping(value = "coupon/{id}/redeem", method = RequestMethod.GET)
    public String redeemCoupon(@PathVariable("id") Integer mid, ModelMap modelMap) {
        modelMap.addAttribute("allCoupons", couponService.findAllCoupons());
        MemberEntity memberEntity = memberService.findByMid(mid);
        modelMap.addAttribute("member", memberEntity);
        return "member/coupon/redeemMemberCoupon";
    }

    @RequestMapping(value = "coupon/{id}/redeem/{cid}", method = RequestMethod.GET)
    public String redeemCouponPost(@PathVariable("id") Integer mid, @PathVariable("cid") Integer cid) {
        MemberCouponEntity memberCouponEntity = new MemberCouponEntity();
        memberCouponEntity.setCouponByCid(couponService.findByCid(cid));
        memberCouponEntity.setMemberByMid(memberService.findByMid(mid));
        memberCouponEntity.setUsage(0);
        couponService.redeemCoupon(memberCouponEntity);

        return "redirect:/member/coupon/" + mid;
    }

}
