package com.krayc.controller;

import com.krayc.model.AdminEntity;
import com.krayc.model.VenueEntity;
import com.krayc.repository.AdminRepository;
import com.krayc.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private VenueRepository venueRepository;

    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String loginMember() {
        return "admin/loginAdmin";
    }

    @RequestMapping(value = "/admin/loginPost", method = RequestMethod.POST)
    public String loginMemberPost(@ModelAttribute("admin") AdminEntity adminEntity, HttpServletRequest request, ModelMap modelMap) {
        AdminEntity adminEntity1 = adminRepository.findByUsername(adminEntity.getUsername());

        if (adminEntity1 != null && adminEntity.getPassword().equals(adminEntity1.getPassword())) {
            System.out.println("Login Success");
            modelMap.addAttribute("admin", adminEntity1);
            return "admin/adminHome";
        } else {
            System.out.println("Login Failed");
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/admin/venues", method = RequestMethod.GET)
    public String getToBePassedVenues(ModelMap modelMap) {
        modelMap.addAttribute("venueList", venueRepository.findByIsPassedIs(Byte.valueOf("0")));
        return "admin/adminVenue";
    }

    @RequestMapping(value = "/admin/venue/good/{vid}", method = RequestMethod.GET)
    public String goodPassVenue(@PathVariable("vid") Integer vid) {
        venueRepository.passVenue(Byte.valueOf("1"), vid);
        venueRepository.flush();
        return "redirect:/admin/venues";
    }

    @RequestMapping(value = "/admin/venue/bad/{vid}", method = RequestMethod.GET)
    public String badPassVenue(@PathVariable("vid") Integer vid) {
        venueRepository.passVenue(Byte.valueOf("0"), vid);
        venueRepository.flush();
        return "redirect:/admin/venues";
    }

}
