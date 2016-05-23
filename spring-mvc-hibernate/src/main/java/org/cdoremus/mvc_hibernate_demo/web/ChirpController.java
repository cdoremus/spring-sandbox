package org.cdoremus.mvc_hibernate_demo.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.cdoremus.mvc_hibernate_demo.data.ChirpRepository;
import org.cdoremus.mvc_hibernate_demo.domain.Chirp;
import org.cdoremus.mvc_hibernate_demo.domain.ChirpUser;

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
//  public List<Chirp> chirps( <- returning chirpRepository.findChirps(max, count) populates 'chipList' attribute
  public String chirps(
      @RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max,
      @RequestParam(value="count", defaultValue="20") int count,
      Model model,
      HttpSession session,
      RedirectAttributes redirectAttributes) {
	  // check that a ChirpUser session attribute has been set
	  ChirpUser user = (ChirpUser)session.getAttribute("chirpUser");
	  if (user == null) {
//		  redirectAttributes.addFlashAttribute("userNotRegistered", "The user needs to be registered before creating chirps");
		  redirectAttributes.addFlashAttribute("userNotRegistered", "User needs to be registered");
		  return "redirect:/chirpUser/register";
	  } else {
		  model.addAttribute("chirpList", chirpRepository.findChirps(max, count));
		  return "chirps";
	  }
  }

  @RequestMapping(value="/{chirpId}", method=RequestMethod.GET)
  public String chirp(
      @PathVariable("chirpId") long chirpId, Model model) {
    model.addAttribute(chirpRepository.findOne(chirpId));
    return "chirp";
  }

  @RequestMapping(method=RequestMethod.POST)
  public String saveChirp(ChirpForm form, Model model) throws Exception {
//	System.out.println("Chirps before save: " + chirpCount());
	
    chirpRepository.save(new Chirp(null, form.getMessage(), new Date(), 
        form.getLongitude(), form.getLatitude()));
    
//	System.out.println("Chirps after save: " + chirpCount());
    
    return "redirect:/chirps";
  }

  
  private int chirpCount() {
	  List<Chirp> chirps = chirpRepository.findChirps(100, 20);
		  return (chirps != null ? chirps.size() : 0);
  }
}
