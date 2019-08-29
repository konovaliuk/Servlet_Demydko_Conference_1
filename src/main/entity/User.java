package entity;


public class User {

  private long id;
  private String name;
  private String surname;
  private String email;
  private String password;
  private String position;


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


  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            ", email='" + email + '\'' +
            ", position='" + position + '\'' +
            '}';
  }
}
