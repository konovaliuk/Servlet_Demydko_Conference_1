package entity;


public class Address {

  private long id;
  private String city;
  private String street;
  private String building;
  private String room;

  public Address(String city, String street, String building, String room) {
    this.city = city;
    this.street = street;
    this.building = building;
    this.room = room;
  }

  public Address() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }


  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }


  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }


  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  @Override
  public String toString() {
    return "Address{" +
            "id=" + id +
            ", city='" + city + '\'' +
            ", street='" + street + '\'' +
            ", building='" + building + '\'' +
            ", room='" + room + '\'' +
            '}';
  }
}
