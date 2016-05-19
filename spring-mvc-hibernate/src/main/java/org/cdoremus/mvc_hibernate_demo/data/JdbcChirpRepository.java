package org.cdoremus.mvc_hibernate_demo.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cdoremus.mvc_hibernate_demo.Chirp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcChirpRepository implements ChirpRepository {

  private JdbcOperations jdbc;

  @Autowired
  public JdbcChirpRepository(JdbcOperations jdbc) {
    this.jdbc = jdbc;
  }

  public List<Chirp> findRecentChirps() {
    return jdbc.query(
        "select id, message, created_at, latitude, longitude" +
        " from Chirp" +
        " order by created_at desc limit 20",
        new SpittleRowMapper());
  }

  public List<Chirp> findChirps(long max, int count) {
    return jdbc.query(
        "select id, message, created_at, latitude, longitude" +
        " from Chirp" +
        " where id < ?" +
        " order by created_at desc limit 20",
        new SpittleRowMapper(), max);
  }

  public Chirp findOne(long id) {
    return jdbc.queryForObject(
        "select id, message, created_at, latitude, longitude" +
        " from Chirp" +
        " where id = ?",
        new SpittleRowMapper(), id);
  }

  public void save(Chirp spittle) {
    jdbc.update(
        "insert into Chirp (message, created_at, latitude, longitude)" +
        " values (?, ?, ?, ?)",
        spittle.getMessage(),
        spittle.getTime(),
        spittle.getLatitude(),
        spittle.getLongitude());
  }

  private static class SpittleRowMapper implements RowMapper<Chirp> {
    public Chirp mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Chirp(
          rs.getLong("id"),
          rs.getString("message"), 
          rs.getDate("created_at"), 
          rs.getDouble("longitude"), 
          rs.getDouble("latitude"));
    }
  }
  
}
