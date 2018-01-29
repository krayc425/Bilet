package com.krayc.controller;

import com.krayc.model.MemberEntity;
import com.krayc.model.VenueEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, ModelMap modelMap) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            if (session.getAttribute("admin") != null) {
                modelMap.addAttribute("admin", session.getAttribute("admin"));
                return "admin/adminDetail";
            } else if (session.getAttribute("member") != null) {
                MemberEntity memberEntity = (MemberEntity) session.getAttribute("member");
                modelMap.addAttribute("member", memberEntity);
                return "redirect:/member/show/" + memberEntity.getMid();
            } else if (session.getAttribute("venue") != null) {
                VenueEntity venueEntity = (VenueEntity) session.getAttribute("venue");
                return "redirect:/venue/show/" + venueEntity.getVid();
            }
        }

        return "index";
    }

}