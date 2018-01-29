package com.krayc.controller;

import com.krayc.model.AdminEntity;
import com.krayc.service.AdminService;
import com.krayc.service.VenueService;
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

@Controller
@RequestMapping(value = "admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private VenueService venueService;

    @RequestMapping(value = "adminHome", method = RequestMethod.GET)
    public String adminHome() {
        return "admin/adminDetail";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView loginMember() {
        return new ModelAndView("admin/loginAdmin");
    }

    @RequestMapping(value = "loginPost", method = RequestMethod.POST)
    public ModelAndView loginMemberPost(@ModelAttribute("admin") AdminEntity adminEntity, HttpServletRequest request, ModelMap modelMap) {
        switch (adminService.login(adminEntity)) {
            case LOGIN_SUCCESS:
                HttpSession session = request.getSession(false);
                session.setAttribute("admin", adminEntity);
                modelMap.addAttribute("admin", adminEntity);
                return new ModelAndView("redirect:/admin/adminHome");
            default:
                MessageVO messageVO = new MessageVO(false, "用户名或密码错误");
                return this.handleMessage(messageVO, "admin/loginAdmin");
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
