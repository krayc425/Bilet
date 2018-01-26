package com.krayc.controller;

import com.krayc.model.SeatEntity;
import com.krayc.model.VenueEntity;
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

    @RequestMapping(value = "/venue/add", method = RequestMethod.GET)
    public String addVenue() {
        return "venue/addVenue";
    }

    @RequestMapping(value = "/venue/addPost", method = RequestMethod.POST)
    public String addMemberPost(@ModelAttribute("member") VenueEntity venueEntity) {
        venueRepository.saveAndFlush(venueEntity);
        return "redirect:/";
    }

    @RequestMapping(value = "/venue/login", method = RequestMethod.GET)
    public String loginVenue() {
        return "venue/loginVenue";
    }

    @RequestMapping(value = "/venue/loginPost", method = RequestMethod.POST)
    public String loginVenuePost(@ModelAttribute("member") VenueEntity venueEntity, HttpServletRequest request, ModelMap modelMap) {
        VenueEntity venueEntity1 = venueRepository.findOne(venueEntity.getVid());

        if (venueEntity.getPassword().equals(venueEntity1.getPassword())) {
            System.out.println("Login Success");
            modelMap.addAttribute("user", venueEntity1);

            HttpSession session = request.getSession(true);
            session.setAttribute("user", venueEntity1);

        } else {
            System.out.println("Login Failed");
        }

        return "venue/venueDetail";
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
    public String addSeats(@PathVariable("id") Integer vid, ModelMap modelMap) {
        modelMap.addAttribute("venue", venueRepository.findOne(vid));
        return "/venue/seat/addSeat";
    }

    @RequestMapping(value = "/venue/{id}/seats/addPost", method = RequestMethod.POST)
    public String addSeatPost(@PathVariable("id") Integer vid, @ModelAttribute("seat") SeatEntity seatEntity) {
        seatEntity.setVenueId(venueRepository.findOne(vid));
        seatRepository.saveAndFlush(seatEntity);
        return "redirect:/venue/" + seatEntity.getVenueId().getVid() + "/seats";
    }

    @RequestMapping(value = "/venue/{id}/seats/delete/{sid}", method = RequestMethod.GET)
    public String deleteSeat(@PathVariable("sid") int sid, @PathVariable("id") int id) {
        seatRepository.delete(sid);
        seatRepository.flush();
        return "redirect:/venue/" + id + "/seats";
    }

    @RequestMapping(value = "/venue/{id}/seats/update/{sid}", method = RequestMethod.GET)
    public String updateSeat(@PathVariable("sid") int sid, @PathVariable("id") int vid, ModelMap modelMap) {
        modelMap.addAttribute("venue", venueRepository.findOne(vid));
        modelMap.addAttribute("seat", seatRepository.findOne(sid));
        return "venue/seat/updateSeat";
    }

    @RequestMapping(value = "/venue/{id}/seats/updatePost/{sid}", method = RequestMethod.POST)
    public String updateSeatPost(@PathVariable("sid") int sid, @PathVariable("id") int vid, @ModelAttribute("seatP") SeatEntity seatEntity) {
        seatRepository.updateSeat(seatEntity.getName(), seatEntity.getNumber(), seatEntity.getSid());
        seatRepository.flush();
        return "redirect:/venue/" + vid + "/seats";
    }

}
