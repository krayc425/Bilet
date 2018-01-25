//package com.krayc.controller;
//
//import com.krayc.repository.VenueRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@Controller
//public class VenueController {
//
//    @Autowired
//    private VenueRepository venueRepository;
//
//    @RequestMapping(value = "/venues/add", method = RequestMethod.GET)
//    public String addVenue() {
//        return "venue/addVenue";
//    }
//
//    @RequestMapping(value = "/venues/addPost", method = RequestMethod.POST)
//    public String addMemberPost(@ModelAttribute("member") VenueEntity venueEntity) {
//        venueRepository.saveAndFlush(venueEntity);
//        return "redirect:/";
//    }
//
//}
