package entity;


import servises.dateTimeManager.DateTimeManager;

import java.util.Date;
import java.sql.Time;

public class Report {


  private Long id;
  private String name;
  private Address address;
  private Date date;
  private Time time;
  private Speaker speaker;
  private boolean isUserRegistered;

  public Report(String name, Address address, Date date, Time time, Speaker speaker) {
    this.name = name;
    this.address = address;
    this.date = date;
    this.time = time;
    this.speaker = speaker;
  }

  public Report(){}

  public Time getTime() {
    return time;
  }

  public void setTime(Time time) {
    this.time = time;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Speaker getSpeaker() {
    return speaker;
  }

  public void setSpeaker(Speaker speaker) {
    this.speaker = speaker;
  }

  public boolean getIsUserRegistered() {
    return isUserRegistered;
  }

  public void setIsUserRegistered(boolean userRegistered) {
    isUserRegistered = userRegistered;
  }

  @Override
  public String toString() {
    return "Report{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address=" + address +
            ", date=" + date +
//          " "+  DateTimeManager.fromTimeToString(time) +
            " , speaker=" + speaker +
            '}';
  }
}
