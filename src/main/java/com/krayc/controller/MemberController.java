package com.krayc.controller;

import com.krayc.model.MemberEntity;
import com.krayc.service.EventService;
import com.krayc.service.LevelService;
import com.krayc.service.MemberService;
import com.krayc.vo.MemberInfoVO;
import com.krayc.vo.MemberUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private EventService eventService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/member/add", method = RequestMethod.GET)
    public String addMember() {
        return "member/addMember";
    }

    @RequestMapping(value = "/member/addPost", method = RequestMethod.POST)
    public String addMemberPost(@ModelAttribute("member") MemberEntity memberEntity) {
        memberService.addMember(memberEntity);
        return "redirect:/";
    }

    @RequestMapping(value = "/member/show/{id}", method = RequestMethod.GET)
    public String showMember(@PathVariable("id") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity1 = memberService.findByMid(mid);
        MemberInfoVO memberInfoVO = new MemberInfoVO(
                memberEntity1.getMid(),
                memberEntity1.getEmail(),
                memberEntity1.getBankAccount(),
                levelService.findLevelEntityWithPoint(memberEntity1.getPoint()).getDescription(),
                memberEntity1.getIsTerminated() == Byte.valueOf("1") ? "已被注销" : "可以使用",
                memberEntity1.getIsEmailPassed() == Byte.valueOf("1") ? "已经激活" : "尚未激活");
        modelMap.addAttribute("member", memberInfoVO);

        modelMap.addAttribute("events", eventService.findAllEvents());

        return "member/memberDetail";
    }

    @RequestMapping(value = "/member/login", method = RequestMethod.GET)
    public String loginMember() {
        return "member/loginMember";
    }

    @RequestMapping(value = "/member/loginPost", method = RequestMethod.POST)
    public String loginMemberPost(@ModelAttribute("member") MemberEntity memberEntity, HttpServletRequest request) {
        switch (memberService.login(memberEntity)) {
            case LOGIN_SUCCESS:
                HttpSession session = request.getSession(false);
                session.setAttribute("member", memberEntity);

                return "redirect:/member/show/" + memberEntity.getMid();
            case LOGIN_NOT_ACTIVATED:
                System.out.println("Not activated");
                return "redirect:/";
            case LOGIN_WRONG_EMAIL_PASSWORD:
                System.out.println("Wrong email and password");
                return "redirect:/";
            case LOGIN_TERMINATED:
                System.out.println("Terminated");
                return "redirect:/";
            default:
                return "redirect:/";
        }
    }

    @RequestMapping(value = "/member/logout", method = RequestMethod.GET)
    public String logoutMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("member", null);
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/member/activate/{id}", method = RequestMethod.GET)
    public String activateMember(@PathVariable("id") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        if (memberEntity.getIsEmailPassed() == 1) {
            System.out.println("激活过了");
        } else {
            memberService.activateMember(mid);
        }
        return "redirect:/member/show/" + mid;
    }

    @RequestMapping(value = "/member/update/{id}", method = RequestMethod.GET)
    public String updateUser(@PathVariable("id") Integer userId, ModelMap modelMap) {
        MemberEntity memberEntity = memberService.findByMid(userId);
        MemberUpdateVO memberUpdateVO = new MemberUpdateVO(memberEntity.getMid(), memberEntity.getBankAccount(), memberEntity.getPassword());

        modelMap.addAttribute("member", memberUpdateVO);
        return "member/updateMember";
    }

    @RequestMapping(value = "/member/updatePost/{id}", method = RequestMethod.POST)
    public String updateUserPost(@PathVariable("id") Integer userId, @ModelAttribute("memberP") MemberEntity user) {
        user.setMid(userId);
        memberService.updateMember(user);
        return "redirect:/member/show/" + userId;
    }

    @RequestMapping(value = "/member/terminate/{id}", method = RequestMethod.GET)
    public String terminateUser(@PathVariable("id") Integer userid) {
        memberService.terminateMember(userid);
        return "redirect:/";
    }

}
