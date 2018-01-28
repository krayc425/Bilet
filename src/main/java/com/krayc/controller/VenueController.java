package com.krayc.controller;

import com.krayc.model.EventEntity;
import com.krayc.model.EventSeatEntity;
import com.krayc.model.SeatEntity;
import com.krayc.model.VenueEntity;
import com.krayc.service.EventService;
import com.krayc.service.SeatService;
import com.krayc.service.VenueService;
import com.krayc.vo.EventVO;
import com.krayc.vo.SeatVO;
import com.krayc.vo.VenueInfoVO;
import com.krayc.vo.VenueUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;

@Controller
public class VenueController {

    @Autowired
    private VenueService venueService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/venue/add", method = RequestMethod.GET)
    public String addVenue() {
        return "venue/addVenue";
    }

    @RequestMapping(value = "/venue/show/{id}", method = RequestMethod.GET)
    public String showVenue(@PathVariable("id") Integer vid, ModelMap modelMap) {
        VenueEntity venueEntity = venueService.findByVid(vid);
        VenueInfoVO venueInfoVO = new VenueInfoVO(venueEntity);
        modelMap.addAttribute("venue", venueInfoVO);
        return "venue/venueDetail";
    }

    @RequestMapping(value = "/venue/addPost", method = RequestMethod.POST)
    public String addMemberPost(@ModelAttribute("venue") VenueEntity venueEntity) {
        venueService.addVenue(venueEntity);
        return "redirect:/";
    }

    @RequestMapping(value = "/venue/login", method = RequestMethod.GET)
    public String loginVenue() {
        return "venue/loginVenue";
    }

    @RequestMapping(value = "/venue/loginPost", method = RequestMethod.POST)
    public String loginVenuePost(@ModelAttribute("venue") VenueEntity venueEntity, HttpServletRequest request, ModelMap modelMap) {
        VenueEntity venueEntity1 = venueService.findByVid(venueEntity.getVid());
        if (venueEntity1 != null && venueEntity.getPassword().equals(venueEntity1.getPassword())) {
            System.out.println("Login Success");

            HttpSession session = request.getSession(false);
            session.setAttribute("venue", venueEntity1);

            return "redirect:/venue/show/" + venueEntity.getVid();
        } else {
            System.out.println("Login Failed");

            return "redirect:/";
        }
    }

    @RequestMapping(value = "/venue/logout", method = RequestMethod.GET)
    public String logoutVenue(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("venue", null);
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/venue/update/{id}", method = RequestMethod.GET)
    public String updateVenue(@PathVariable("id") Integer vid, ModelMap modelMap) {
        VenueEntity venueEntity = venueService.findByVid(vid);
        VenueUpdateVO venueUpdateVO = new VenueUpdateVO(venueEntity);
        modelMap.addAttribute("venue", venueUpdateVO);
        return "venue/updateVenue";
    }

    @RequestMapping(value = "/venue/updatePost", method = RequestMethod.POST)
    public String updateVenuePost(@ModelAttribute("venueP") VenueEntity venueEntity) {
        venueService.updateVenue(venueEntity);
        return "redirect:/venue/show/" + venueEntity.getVid();
    }

    @RequestMapping(value = "/venue/{id}/seats", method = RequestMethod.GET)
    public String seats(@PathVariable("id") Integer vid, ModelMap modelMap) {
        VenueEntity venueEntity = venueService.findByVid(vid);
        Collection<SeatVO> seatVOS = new ArrayList<SeatVO>();
        for (SeatEntity entity : venueEntity.getSeatsByVid()) {
            seatVOS.add(new SeatVO(entity));
        }
        modelMap.addAttribute("seats", seatVOS);
        modelMap.addAttribute("vid", vid);
        return "/venue/venueSeats";
    }

    @RequestMapping(value = "/venue/{id}/seats/add", method = RequestMethod.GET)
    public String addSeat(@PathVariable("id") Integer vid, ModelMap modelMap) {
        modelMap.addAttribute("vid", vid);
        return "/venue/seat/addSeat";
    }

    @RequestMapping(value = "/venue/{id}/seats/addPost", method = RequestMethod.POST)
    public String addSeatPost(@PathVariable("id") Integer vid, @ModelAttribute("seat") SeatEntity seatEntity) {
        seatEntity.setVenueId(venueService.findByVid(vid));
        seatService.addSeat(seatEntity);
        return "redirect:/venue/" + vid + "/seats";
    }

    @RequestMapping(value = "/venue/{vid}/seats/delete/{sid}", method = RequestMethod.GET)
    public String deleteSeat(@PathVariable("sid") int sid, @PathVariable("vid") int vid) {
        seatService.deleteSeat(sid);
        return "redirect:/venue/" + vid + "/seats";
    }

    @RequestMapping(value = "/venue/{id}/seats/update/{sid}", method = RequestMethod.GET)
    public String updateSeat(@PathVariable("sid") int sid, @PathVariable("id") int vid, ModelMap modelMap) {
        modelMap.addAttribute("vid", vid);
        modelMap.addAttribute("seat", new SeatVO(seatService.findBySid(sid)));
        return "venue/seat/updateSeat";
    }

    @RequestMapping(value = "/venue/{id}/seats/updatePost/{sid}", method = RequestMethod.POST)
    public String updateSeatPost(@PathVariable("sid") int sid, @PathVariable("id") int vid, @ModelAttribute("seatP") SeatEntity seatEntity) {
        seatEntity.setSid(sid);
        seatService.updateSeat(seatEntity);
        return "redirect:/venue/" + vid + "/seats";
    }

    @RequestMapping(value = "/venue/{vid}/events", method = RequestMethod.GET)
    public String events(@PathVariable("vid") Integer vid, ModelMap modelMap) {
        VenueEntity venueEntity = venueService.findByVid(vid);
        modelMap.addAttribute("vid", vid);
        Collection<EventVO> eventVOS = new ArrayList<EventVO>();
        for (EventEntity eventEntity : venueEntity.getEventsByVid()) {
            eventVOS.add(new EventVO(eventEntity));
        }
        modelMap.addAttribute("events", eventVOS);
        modelMap.addAttribute("eventTypes", eventService.findAllEventTypes());
        return "venue/venueEvents";
    }

    @RequestMapping(value = "/venue/{vid}/events/add", method = RequestMethod.GET)
    public String addEvent(@PathVariable("vid") Integer vid, ModelMap modelMap) {
        modelMap.addAttribute("vid", vid);
        modelMap.addAttribute("eventTypes", eventService.findAllEventTypes());
        return "/venue/event/addEvent";
    }

    @RequestMapping(value = "/venue/{vid}/events/addPost", method = RequestMethod.POST)
    public String addEventPost(@PathVariable("vid") Integer vid, HttpServletRequest request) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setName(request.getParameter("name"));
        eventEntity.setTime(request.getParameter("time"));
        eventEntity.setDescription(request.getParameter("description"));
        eventEntity.setVenueId(venueService.findByVid(vid));
        eventEntity.setEventTypeEntity(eventService.findTypeByTid(Integer.parseInt(request.getParameter("eventTypeEntity"))));
        eventService.addEvent(eventEntity);
        return "redirect:/venue/" + vid + "/events/" + eventEntity.getEid() + "/seats";
    }

    @RequestMapping(value = "/venue/{vid}/events/{eid}/seats")
    public String eventSeats(@PathVariable("eid") Integer eid, @PathVariable("vid") Integer vid, ModelMap modelMap) {
        VenueEntity venueEntity = venueService.findByVid(vid);
        modelMap.addAttribute("venue", venueEntity);
        EventEntity eventEntity = eventService.findByEid(eid);
        modelMap.addAttribute("event", eventEntity);

        Collection<EventSeatEntity> alreadySeats = eventService.findEventSeatOtherThanSeatsAndInEvent(venueEntity.getSeatsByVid(), eventEntity);

        modelMap.addAttribute("eventSeats", alreadySeats);
        return "/venue/event/eventSeats";
    }

    @RequestMapping(value = "/venue/{vid}/events/{eid}/seats/add")
    public String addEventSeats(@PathVariable("eid") Integer eid, @PathVariable("vid") Integer vid, ModelMap modelMap) {
        VenueEntity venueEntity = venueService.findByVid(vid);
        modelMap.addAttribute("venue", venueEntity);
        EventEntity eventEntity = eventService.findByEid(eid);
        modelMap.addAttribute("event", eventEntity);

        Collection<EventSeatEntity> alreadySeats = eventService.findEventSeatOtherThanSeatsAndInEvent(venueEntity.getSeatsByVid(), eventEntity);
        Collection<SeatEntity> toBeAddedSeats = venueEntity.getSeatsByVid();

        for (EventSeatEntity eventSeatEntity : alreadySeats) {
            toBeAddedSeats.remove(eventSeatEntity.getSeat());
        }

        modelMap.addAttribute("eventSeats", toBeAddedSeats);

        return "/venue/event/addEventSeat";
    }

    @RequestMapping(value = "/venue/{vid}/events/{eid}/seats/addPost")
    public String addEventSeatsPost(@PathVariable("eid") Integer eid, @PathVariable("vid") Integer vid, HttpServletRequest request) {
        EventSeatEntity eventSeatEntity = new EventSeatEntity();
        eventSeatEntity.setEvent(eventService.findByEid(eid));
        eventSeatEntity.setSeat(seatService.findBySid(Integer.parseInt(request.getParameter("seatId"))));
        eventSeatEntity.setNumber(Integer.parseInt(request.getParameter("number")));
        eventSeatEntity.setPrice(Integer.parseInt(request.getParameter("price")));
        eventService.addEventSeat(eventSeatEntity);
        return "redirect:/venue/" + vid + "/events/" + eid + "/seats";
    }

}
