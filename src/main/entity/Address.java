package entity;


import java.util.Objects;

public class Address {

  private Long id;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Address address = (Address) o;
    return Objects.equals(id, address.id) &&
            Objects.equals(city, address.city) &&
            Objects.equals(street, address.street) &&
            Objects.equals(building, address.building) &&
            Objects.equals(room, address.room);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, city, street, building, room);
  }
}
