package com.krayc.controller;

import com.krayc.model.MemberEntity;
import com.krayc.repository.MemberRepository;
import io.github.biezhi.ome.OhMyEmail;
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

    @RequestMapping(value = "/member/login", method = RequestMethod.GET)
    public String loginMember() {
        return "member/loginMember";
    }

    @RequestMapping(value = "/member/loginPost", method = RequestMethod.POST)
    public String loginMemberPost(@ModelAttribute("member") MemberEntity memberEntity, HttpServletRequest request, ModelMap modelMap) {
        System.out.println("Input Email " + memberEntity.getEmail());
        System.out.println("Input password " + memberEntity.getPassword());

        MemberEntity memberEntity1 = memberRepository.findByEmail(memberEntity.getEmail());

        if (memberEntity.getPassword().equals(memberEntity1.getPassword()) && memberEntity1.getIsEmailPassed() == 1) {
            System.out.println("Login Success");
            modelMap.addAttribute("user", memberEntity1);

            HttpSession session = request.getSession(true);
            session.setAttribute("user", memberEntity1);

        } else {
            System.out.println("Login Failed");
        }

        return "member/memberDetail";
    }

    @RequestMapping(value = "/member/activate/{id}", method = RequestMethod.GET)
    public String activateMember(@PathVariable("id") Integer mid, ModelMap modelMap) {
        System.out.println("Mid " + mid);
        MemberEntity memberEntity = memberRepository.findOne(mid);
        if (memberEntity.getIsEmailPassed() == 1) {
            System.out.println("激活过了");
        } else {
            memberRepository.updateActivationMember(mid, Byte.valueOf("1"));

            modelMap.addAttribute("user", memberEntity);
        }
        return "member/memberDetail";
    }

    @RequestMapping(value = "/member/show/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable("id") Integer memberId, ModelMap modelMap) {
        MemberEntity memberEntity = memberRepository.findOne(memberId);
        modelMap.addAttribute("user", memberEntity);
        return "member/memberDetail";
    }

    @RequestMapping(value = "/member/update/{id}", method = RequestMethod.GET)
    public String updateUser(@PathVariable("id") Integer userId, ModelMap modelMap) {
        MemberEntity userEntity = memberRepository.findOne(userId);
        modelMap.addAttribute("user", userEntity);
        return "member/updateMember";
    }

    @RequestMapping(value = "/member/updatePost", method = RequestMethod.POST)
    public String updateUserPost(@ModelAttribute("memberP") MemberEntity user) {
        memberRepository.updateMember(user.getEmail(), user.getPassword(), user.getMid());
        memberRepository.flush(); // 刷新缓冲区
        return "redirect:/";
    }

    @RequestMapping(value = "/member/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") Integer userId) {
        memberRepository.delete(userId);
        memberRepository.flush();
        return "redirect:/";
    }

}
