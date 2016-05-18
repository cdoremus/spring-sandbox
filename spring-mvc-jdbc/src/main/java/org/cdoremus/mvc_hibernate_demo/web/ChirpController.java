package org.cdoremus.mvc_hibernate_demo.web;

import java.util.Date;
import java.util.List;

import org.cdoremus.mvc_hibernate_demo.Chirp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.cdoremus.mvc_hibernate_demo.data.ChirpRepository;

@Controller
@RequestMapping("/chirps")
public class ChirpController {

  private static final String MAX_LONG_AS_STRING = "9223372036854775807";
  
  private ChirpRepository chirpRepository;

  @Autowired
  public ChirpController(ChirpRepository chirpRepository) {
    this.chirpRepository = chirpRepository;
  }

  @RequestMapping(method=RequestMethod.GET)
  public List<Chirp> chirps(
      @RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max,
      @RequestParam(value="count", defaultValue="20") int count) {
    return chirpRepository.findChirps(max, count);
  }

  @RequestMapping(value="/{chirpId}", method=RequestMethod.GET)
  public String chirp(
      @PathVariable("chirpId") long chirpId, Model model) {
    model.addAttribute(chirpRepository.findOne(chirpId));
    return "spittle";
  }

  @RequestMapping(method=RequestMethod.POST)
  public String saveChirp(ChirpForm form, Model model) throws Exception {
	System.out.println("Chirps before save: " + chirpCount());
	
    chirpRepository.save(new Chirp(null, form.getMessage(), new Date(), 
        form.getLongitude(), form.getLatitude()));
    
	System.out.println("Chirps after save: " + chirpCount());
    
    return "redirect:/chirps";
  }

  
  private int chirpCount() {
	  List<Chirp> chirps = chirpRepository.findChirps(100, 20);
		  return (chirps != null ? chirps.size() : 0);
  }
}
