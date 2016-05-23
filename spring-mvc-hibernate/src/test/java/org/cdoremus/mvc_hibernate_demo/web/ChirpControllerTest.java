package org.cdoremus.mvc_hibernate_demo.web;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import org.cdoremus.mvc_hibernate_demo.data.ChirpRepository;
import org.cdoremus.mvc_hibernate_demo.domain.Chirp;

public class ChirpControllerTest {

  @Test
  public void shouldShowRecentChirps() throws Exception {
    List<Chirp> expectedChrips = createChirpList(20);
    ChirpRepository mockRepository = mock(ChirpRepository.class);
    when(mockRepository.findChirps(Long.MAX_VALUE, 20))
        .thenReturn(expectedChrips);

    ChirpController controller = new ChirpController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller)
        .setSingleView(new InternalResourceView("/WEB-INF/views/chirps.jsp"))
        .build();

    mockMvc.perform(get("/chirps"))
       .andExpect(view().name("chirps"))
       .andExpect(model().attributeExists("chirpList"))
       .andExpect(model().attribute("chirpList", 
                  hasItems(expectedChrips.toArray())));
  }

  @Test
  public void shouldShowPagedChirps() throws Exception {
    List<Chirp> expectedChrips = createChirpList(50);
    ChirpRepository mockRepository = mock(ChirpRepository.class);
    when(mockRepository.findChirps(238900, 50))
        .thenReturn(expectedChrips);
    
    ChirpController controller = new ChirpController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller)
        .setSingleView(new InternalResourceView("/WEB-INF/views/chirps.jsp"))
        .build();

    mockMvc.perform(get("/chirps?max=238900&count=50"))
      .andExpect(view().name("chirps"))
      .andExpect(model().attributeExists("chirpList"))
      .andExpect(model().attribute("chirpList", 
                 hasItems(expectedChrips.toArray())));
  }
  
  @Test
  public void testChirp() throws Exception {
    Chirp expectedChirp = new Chirp("Hello", new Date());
    ChirpRepository mockRepository = mock(ChirpRepository.class);
    when(mockRepository.findOne(12345)).thenReturn(expectedChirp);
    
    ChirpController controller = new ChirpController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();

    mockMvc.perform(get("/chirps/12345"))
      .andExpect(view().name("chirp"))
      .andExpect(model().attributeExists("chirp"))
      .andExpect(model().attribute("chirp", expectedChirp));
  }

  @Test
  public void saveChirp() throws Exception {
    ChirpRepository mockRepository = mock(ChirpRepository.class);
    ChirpController controller = new ChirpController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();

    mockMvc.perform(post("/chirps")
           .param("message", "Hello World") // this works, but isn't really testing what really happens
           .param("longitude", "-81.5811668")
           .param("latitude", "28.4159649")
           )
           .andExpect(redirectedUrl("/chirps"));
    
    verify(mockRepository, atLeastOnce()).save(new Chirp(null, "Hello World", new Date(), -81.5811668, 28.4159649));
  }
  
  private List<Chirp> createChirpList(int count) {
    List<Chirp> spittles = new ArrayList<Chirp>();
    for (int i=0; i < count; i++) {
      spittles.add(new Chirp("Chirp " + i, new Date()));
    }
    return spittles;
  }
}
