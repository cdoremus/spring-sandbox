package org.cdoremus.mvc_hibernate_demo.data;

import org.cdoremus.mvc_hibernate_demo.domain.ChirpUser;

public interface ChirpUserRepository {

  ChirpUser save(ChirpUser user);
  
  ChirpUser findByUsername(String username);

}
