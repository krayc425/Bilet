package com.krayc.controller;

import com.krayc.model.MemberEntity;
import com.krayc.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

        System.out.println("Send an Email");

        return "redirect:/";
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
        memberRepository.updateUser(user.getEmail(), user.getPassword(), user.getMid());
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
