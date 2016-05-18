package org.cdoremus.mvc_hibernate_demo.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cdoremus.mvc_hibernate_demo.ChirpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcChirpUserRepository implements ChirpUserRepository {
  
  private JdbcOperations jdbc;

  @Autowired
  public JdbcChirpUserRepository(JdbcOperations jdbc) {
    this.jdbc = jdbc;
  }

  public ChirpUser save(ChirpUser user) {
    jdbc.update(
        "insert into ChirpUser (username, password, first_name, last_name, email)" +
        " values (?, ?, ?, ?, ?)",
        user.getUsername(),
        user.getPassword(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail());
    return user; // TODO: Determine value for id
  }

  public ChirpUser findByUsername(String username) {
    return jdbc.queryForObject(
        "select id, username, null, first_name, last_name, email from ChirpUser where username=?", 
        new ChirpUserRowMapper(), 
        username);
  }
  
  private static class ChirpUserRowMapper implements RowMapper<ChirpUser> {
    public ChirpUser mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new ChirpUser(
          rs.getLong("id"),
          rs.getString("username"),
          null,
          rs.getString("first_name"),
          rs.getString("last_name"),
          rs.getString("email"));
    }
  }

}
