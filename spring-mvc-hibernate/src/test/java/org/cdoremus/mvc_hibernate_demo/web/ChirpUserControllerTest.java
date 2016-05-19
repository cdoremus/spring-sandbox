package org.cdoremus.mvc_hibernate_demo.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import org.cdoremus.mvc_hibernate_demo.data.ChirpUserRepository;
import org.cdoremus.mvc_hibernate_demo.domain.ChirpUser;

public class ChirpUserControllerTest {

  @Test
  public void shouldShowRegistration() throws Exception {
    ChirpUserRepository mockRepository = mock(ChirpUserRepository.class);
    ChirpUserController controller = new ChirpUserController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();
    mockMvc.perform(get("/chirpUser/register"))
           .andExpect(view().name("registerForm"));
  }
  
  @Test
  public void shouldProcessRegistration() throws Exception {
    ChirpUserRepository mockRepository = mock(ChirpUserRepository.class);
    ChirpUser unsaved = new ChirpUser("jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
    ChirpUser saved = new ChirpUser(24L, "jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
    when(mockRepository.save(unsaved)).thenReturn(saved);
    
    ChirpUserController controller = new ChirpUserController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();

    mockMvc.perform(post("/chirpUser/register")
           .param("firstName", "Jack")
           .param("lastName", "Bauer")
           .param("username", "jbauer")
           .param("password", "24hours")
           .param("email", "jbauer@ctu.gov"))
           .andExpect(redirectedUrl("/chirpUser/jbauer"));
    
    verify(mockRepository, atLeastOnce()).save(unsaved);
  }

  @Test
  public void shouldFailValidationWithNoData() throws Exception {
    ChirpUserRepository mockRepository = mock(ChirpUserRepository.class);    
    ChirpUserController controller = new ChirpUserController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();
    
    mockMvc.perform(post("/chirpUser/register"))
        .andExpect(status().isOk())
        .andExpect(view().name("registerForm"))
        .andExpect(model().errorCount(5))
        .andExpect(model().attributeHasFieldErrors(
            "chirpUser", "firstName", "lastName", "username", "password", "email"));
  }

}
