package com.krayc.controller;

import com.krayc.model.EventEntity;
import com.krayc.model.SeatEntity;
import com.krayc.model.VenueEntity;
import com.krayc.repository.EventRepository;
import com.krayc.repository.EventTypeRepository;
import com.krayc.repository.SeatRepository;
import com.krayc.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

@Controller
public class VenueController {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @RequestMapping(value = "/venue/add", method = RequestMethod.GET)
    public String addVenue() {
        return "venue/addVenue";
    }

    @RequestMapping(value = "/venue/show/{id}", method = RequestMethod.GET)
    public String showVenue(@PathVariable("id") Integer vid, ModelMap modelMap) {
        modelMap.addAttribute("venue", venueRepository.findOne(vid));
        return "venue/venueDetail";
    }

    @RequestMapping(value = "/venue/addPost", method = RequestMethod.POST)
    public String addMemberPost(@ModelAttribute("venue") VenueEntity venueEntity) {
        venueRepository.saveAndFlush(venueEntity);
        return "redirect:/";
    }

    @RequestMapping(value = "/venue/login", method = RequestMethod.GET)
    public String loginVenue() {
        return "venue/loginVenue";
    }

    @RequestMapping(value = "/venue/loginPost", method = RequestMethod.POST)
    public String loginVenuePost(@ModelAttribute("venue") VenueEntity venueEntity, HttpServletRequest request, ModelMap modelMap) {
        VenueEntity venueEntity1 = venueRepository.findOne(venueEntity.getVid());
        if (venueEntity1 != null && venueEntity.getPassword().equals(venueEntity1.getPassword())) {
            System.out.println("Login Success");
            modelMap.addAttribute("venue", venueEntity1);

            HttpSession session = request.getSession(true);
            session.setAttribute("venue", venueEntity1);

            return "redirect:/venue/show/" + venueEntity.getVid();
        } else {
            System.out.println("Login Failed");

            return "redirect:/";
        }
    }

    @RequestMapping(value = "/venue/update/{id}", method = RequestMethod.GET)
    public String updateVenue(@PathVariable("id") Integer vid, ModelMap modelMap) {
        VenueEntity venueEntity = venueRepository.findOne(vid);
        modelMap.addAttribute("venue", venueEntity);
        return "venue/updateVenue";
    }

    @RequestMapping(value = "/venue/updatePost", method = RequestMethod.POST)
    public String updateVenuePost(@ModelAttribute("venueP") VenueEntity venueEntity) {
        venueRepository.updateVenue(venueEntity.getName(), venueEntity.getPassword(), venueEntity.getAddress(), venueEntity.getVid());
        venueRepository.passVenue(Byte.valueOf("0"), venueEntity.getVid());
        venueRepository.flush();
        return "redirect:/venue/show/" + venueEntity.getVid();
    }

    @RequestMapping(value = "/venue/{id}/seats", method = RequestMethod.GET)
    public String seats(@PathVariable("id") Integer vid, ModelMap modelMap) {
        VenueEntity venueEntity = venueRepository.findOne(vid);
        Collection<SeatEntity> seatEntities = venueEntity.getSeatsByVid();

        modelMap.addAttribute("seats", seatEntities);
        modelMap.addAttribute("venue", venueEntity);

        return "/venue/venueSeats";
    }

    @RequestMapping(value = "/venue/{id}/seats/add", method = RequestMethod.GET)
    public String addSeat(@PathVariable("id") Integer vid, ModelMap modelMap) {
        modelMap.addAttribute("venue", venueRepository.findOne(vid));
        return "/venue/seat/addSeat";
    }

    @RequestMapping(value = "/venue/{id}/seats/addPost", method = RequestMethod.POST)
    public String addSeatPost(@PathVariable("id") Integer vid, @ModelAttribute("seat") SeatEntity seatEntity) {
        seatEntity.setVenueId(venueRepository.findOne(vid));
        seatRepository.saveAndFlush(seatEntity);
        return "redirect:/venue/" + vid + "/seats";
    }

    @RequestMapping(value = "/venue/{vid}/seats/delete/{sid}", method = RequestMethod.GET)
    public String deleteSeat(@PathVariable("sid") int sid, @PathVariable("vid") int vid) {
        seatRepository.delete(sid);
        seatRepository.flush();
        return "redirect:/venue/" + vid + "/seats";
    }

    @RequestMapping(value = "/venue/{id}/seats/update/{sid}", method = RequestMethod.GET)
    public String updateSeat(@PathVariable("sid") int sid, @PathVariable("id") int vid, ModelMap modelMap) {
        modelMap.addAttribute("venue", venueRepository.findOne(vid));
        modelMap.addAttribute("seat", seatRepository.findOne(sid));
        return "venue/seat/updateSeat";
    }

    @RequestMapping(value = "/venue/{id}/seats/updatePost/{sid}", method = RequestMethod.POST)
    public String updateSeatPost(@PathVariable("sid") int sid, @PathVariable("id") int vid, @ModelAttribute("seatP") SeatEntity seatEntity) {
        seatRepository.updateSeat(seatEntity.getName(), seatEntity.getNumber(), sid);
        seatRepository.flush();
        return "redirect:/venue/" + vid + "/seats";
    }

    @RequestMapping(value = "/venue/{vid}/events", method = RequestMethod.GET)
    public String events(@PathVariable("vid") Integer id, ModelMap modelMap) {
        VenueEntity venueEntity = venueRepository.findOne(id);
        modelMap.addAttribute("venue", venueEntity);
        modelMap.addAttribute("events", venueEntity.getEventsByVid());
        modelMap.addAttribute("eventTypes", eventTypeRepository.findAll());
        return "venue/venueEvents";
    }

    @RequestMapping(value = "/venue/{vid}/events/add", method = RequestMethod.GET)
    public String addEvent(@PathVariable("vid") Integer vid, ModelMap modelMap) {
        modelMap.addAttribute("venue", venueRepository.findOne(vid));
        modelMap.addAttribute("eventTypes", eventTypeRepository.findAll());
        return "/venue/event/addEvent";
    }

    @RequestMapping(value = "/venue/{vid}/events/addPost", method = RequestMethod.POST)
    public String addEventPost(@PathVariable("vid") Integer vid, @ModelAttribute("event") EventEntity eventEntity) {
        eventEntity.setVenueId(venueRepository.findOne(vid));
        eventRepository.saveAndFlush(eventEntity);
        return "redirect:/venue/" + vid + "/events";
    }

}
