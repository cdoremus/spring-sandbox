package org.cdoremus.mvc_hibernate_demo.data;

import java.util.List;

import org.cdoremus.mvc_hibernate_demo.domain.Chirp;

public interface ChirpRepository {

  List<Chirp> findRecentChirps();

  List<Chirp> findChirps(long max, int count);
  
  Chirp findOne(long id);

  void save(Chirp spittle);

}
