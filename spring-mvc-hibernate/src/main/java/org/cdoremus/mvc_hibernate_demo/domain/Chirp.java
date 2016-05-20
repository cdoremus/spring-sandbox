package org.cdoremus.mvc_hibernate_demo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class Chirp {
  @Id
  @GeneratedValue
  private final Long id;
  
  @Column(nullable=false)
  private final String message;
  
  @Column(name="created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private final Date time;
  
  @Column
  private Double latitude;
  
  @Column
  private Double longitude;

  public Chirp() {
	  id = null;
	  message = null;
	  time = new Date();
  }
  
  public Chirp(String message, Date time) {
    this(null, message, time, null, null);
  }
  
  public Chirp(Long id, String message, Date time, Double longitude, Double latitude) {
    this.id = id;
    this.message = message;
    this.time = time;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public long getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }

  public Date getTime() {
    return time;
  }
  
  public Double getLongitude() {
    return longitude;
  }
  
  public Double getLatitude() {
    return latitude;
  }
  
  @Override
  public boolean equals(Object that) {
    return EqualsBuilder.reflectionEquals(this, that, "id", "time");
  }
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, "id", "time");
  }
  
}
