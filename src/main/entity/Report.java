package entity;


import servises.dateTimeManager.DateTimeManager;

import java.util.Date;
import java.sql.Time;

public class Report {


  private long id;
  private String name;
  private Address address;
  private Date date;
  private Time time;
  private Speaker speaker;


  public Time getTime() {
    return time;
  }

  public void setTime(Time time) {
    this.time = time;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  @Override
  public String toString() {
    return "Report{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address=" + address +
            ", date=" + date +
          " "+  DateTimeManager.fromTimeToString(time) +
            " , speaker=" + speaker +
            '}';
  }
}
