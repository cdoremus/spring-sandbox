package org.cdoremus.mvc_hibernate_demo.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.cdoremus.mvc_hibernate_demo.data.ChirpUserRepository;
import org.cdoremus.mvc_hibernate_demo.domain.ChirpUser;

@Controller
@RequestMapping("/chirpUser")
@SessionAttributes("chirpUser")
public class ChirpUserController {

  private ChirpUserRepository userRepository;

  @Autowired
  public ChirpUserController(ChirpUserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  @RequestMapping(value="/register", method=GET)
  public String showRegistrationForm(Model model) {
	if (!model.containsAttribute("chirpUser")) {  
		model.addAttribute(new ChirpUser());
	}
    return "registerForm";
  }
  
  @RequestMapping(value="/register", method=POST)
  public String processRegistration(
      @Valid ChirpUser user, 
      Errors errors) {
    if (errors.hasErrors()) {
      return "registerForm";
    }
    
//    System.out.println("User being registered: " + user);
    userRepository.save(user);
    ChirpUser newuser = userRepository.findByUsername(user.getUsername());
//    System.out.println("User registered: " + newuser);
    
    return "redirect:/chirpUser/" + user.getUsername();
  }
  
  @RequestMapping(value="/{username}", method=GET)
  public String showChriperProfile(@PathVariable String username, Model model) {
    System.out.println("Username of user to display on profile page: " + username);
    ChirpUser chirpUser = userRepository.findByUsername(username);
    model.addAttribute(chirpUser);
    return "profile";
  }
  
}
