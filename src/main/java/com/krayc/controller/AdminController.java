package com.krayc.controller;

import com.krayc.model.AdminEntity;
import com.krayc.repository.AdminRepository;
import com.krayc.service.VenueService;
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
@RequestMapping(value = "admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private VenueService venueService;

    @RequestMapping(value = "adminHome", method = RequestMethod.GET)
    public String adminHome() {
        return "admin/adminHome";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginMember() {
        return "admin/loginAdmin";
    }

    @RequestMapping(value = "loginPost", method = RequestMethod.POST)
    public String loginMemberPost(@ModelAttribute("admin") AdminEntity adminEntity, HttpServletRequest request, ModelMap modelMap) {
        AdminEntity adminEntity1 = adminRepository.findByUsername(adminEntity.getUsername());
        if (adminEntity1 != null && adminEntity.getPassword().equals(adminEntity1.getPassword())) {
            System.out.println("Login Success");
            HttpSession session = request.getSession(false);
            session.setAttribute("admin", adminEntity1);

            modelMap.addAttribute("admin", adminEntity1);
            return "redirect:/admin/adminHome";
        } else {
            System.out.println("Login Failed");
            return "redirect:/";
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logoutVenue(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("admin ", null);
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "venues", method = RequestMethod.GET)
    public String getToBePassedVenues(ModelMap modelMap) {
        modelMap.addAttribute("venueList", venueService.findToBePassedVenues());
        return "admin/adminVenue";
    }

    @RequestMapping(value = "venue/good/{vid}", method = RequestMethod.GET)
    public String goodPassVenue(@PathVariable("vid") Integer vid) {
        venueService.passVenueOrNot(vid, true);
        return "redirect:/admin/venues";
    }

    @RequestMapping(value = "venue/bad/{vid}", method = RequestMethod.GET)
    public String badPassVenue(@PathVariable("vid") Integer vid) {
        venueService.passVenueOrNot(vid, false);
        return "redirect:/admin/venues";
    }

}
