package com.krayc.controller;

import com.krayc.model.MemberEntity;
import com.krayc.repository.MemberRepository;
import io.github.biezhi.ome.OhMyEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
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
    private MemberRepository memberRepository;

    @RequestMapping(value = "/member/add", method = RequestMethod.GET)
    public String addMember() {
        return "member/addMember";
    }

    @RequestMapping(value = "/member/addPost", method = RequestMethod.POST)
    public String addMemberPost(@ModelAttribute("member") MemberEntity memberEntity) {
        memberRepository.saveAndFlush(memberEntity);

        MemberEntity memberEntity1 = memberRepository.findByEmail(memberEntity.getEmail());

        OhMyEmail.config(OhMyEmail.SMTP_163(false), "krayc425@163.com", "songkuixi2");
        try {
            OhMyEmail.subject("Welcome to Bilet!")
                    .from("Bilet")
                    .to(memberEntity.getEmail())
                    .text("http://localhost:8080/member/activate/" + memberEntity1.getMid())
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/member/show/{id}", method = RequestMethod.GET)
    public String showMember(@PathVariable("id") Integer mid, ModelMap modelMap) {
        modelMap.addAttribute("user", memberRepository.findOne(mid));
        return "member/memberDetail";
    }

    @RequestMapping(value = "/member/login", method = RequestMethod.GET)
    public String loginMember() {
        return "member/loginMember";
    }

    @RequestMapping(value = "/member/loginPost", method = RequestMethod.POST)
    public String loginMemberPost(@ModelAttribute("member") MemberEntity memberEntity, HttpServletRequest request, ModelMap modelMap) {
        MemberEntity memberEntity1 = memberRepository.findByEmail(memberEntity.getEmail());

        if (memberEntity1 != null && memberEntity.getPassword().equals(memberEntity1.getPassword())
                && memberEntity1.getIsEmailPassed() == 1
                && memberEntity1.getIsTerminated() != 1) {
            System.out.println("Login Success");
            modelMap.addAttribute("user", memberEntity1);

            HttpSession session = request.getSession(true);
            session.setAttribute("user", memberEntity1);

            return "redirect:/member/show/" + memberEntity1.getMid();
        } else {
            System.out.println("Login Failed");

            return "redirect:/";
        }
    }

    @RequestMapping(value = "/member/activate/{id}", method = RequestMethod.GET)
    public String activateMember(@PathVariable("id") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity = memberRepository.findOne(mid);
        if (memberEntity.getIsEmailPassed() == 1) {
            System.out.println("激活过了");
        } else {
            memberRepository.updateActivationMember(mid, Byte.valueOf("1"));

            modelMap.addAttribute("user", memberEntity);
        }
        return "redirect:/member/show/" + mid;
    }

    @RequestMapping(value = "/member/update/{id}", method = RequestMethod.GET)
    public String updateUser(@PathVariable("id") Integer userId, ModelMap modelMap) {
        modelMap.addAttribute("user", memberRepository.findOne(userId));
        return "member/updateMember";
    }

    @RequestMapping(value = "/member/updatePost/{id}", method = RequestMethod.POST)
    public String updateUserPost(@PathVariable("id") Integer userId, @ModelAttribute("memberP") MemberEntity user) {
        memberRepository.updateMember(user.getEmail(), user.getPassword(), user.getBankAccount(), userId);
        memberRepository.flush();
        return "redirect:/member/show/" + userId;
    }

    @RequestMapping(value = "/member/terminate/{id}", method = RequestMethod.GET)
    public String terminateUser(@PathVariable("id") Integer userid) {
        memberRepository.terminateMember(userid, Byte.valueOf("1"));
        return "redirect:/";
    }

}
