package com.krayc.controller;

import com.krayc.model.*;
import com.krayc.service.AdminService;
import com.krayc.service.EventService;
import com.krayc.service.VenueService;
import com.krayc.vo.AdminBookVO;
import com.krayc.vo.EventVO;
import com.krayc.vo.MessageVO;
import com.krayc.vo.VenueBookVO;
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
import java.util.ArrayList;

@Controller
@RequestMapping(value = "admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private VenueService venueService;

    @Autowired
    private EventService eventService;

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
    public String venues(ModelMap modelMap) {
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

    @RequestMapping(value = "events", method = RequestMethod.GET)
    public String events(ModelMap modelMap) {
        ArrayList<EventVO> eventVOS = new ArrayList<EventVO>();
        for (EventEntity eventEntity : eventService.findAllEvents()) {
            eventVOS.add(new EventVO(eventEntity));
        }
        modelMap.addAttribute("eventList", eventVOS);
        return "admin/adminEvent";
    }

    @RequestMapping(value = "events/confirm/{eid}", method = RequestMethod.GET)
    public String confirmEvent(@PathVariable("eid") Integer eid) {
        EventEntity eventEntity = eventService.findByEid(eid);
        adminService.confirmEvent(eventEntity);
        return "redirect:/admin/events";
    }

    @RequestMapping(value = "books", method = RequestMethod.GET)
    public String books(ModelMap modelMap) {
        ArrayList<AdminBookVO> adminBookVOS = new ArrayList<AdminBookVO>();
        for (AdminBookEntity entity : adminService.getAllAdminBooks()) {
            adminBookVOS.add(new AdminBookVO(entity));
        }
        modelMap.addAttribute("books", adminBookVOS);
        return "admin/adminBooks";
    }

}
