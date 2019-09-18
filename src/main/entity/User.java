package entity;


import java.util.Objects;

public class User {

  private Long id;
  private String name;
  private String surname;
  private String email;
  private String password;
  private String position;
  private String language;

  public User(String name, String surname, String email, String password, String position, String language) {
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.password = password;
    this.position = position;
    this.language = language;
  }

  public User(){}

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

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id.equals(user.id) &&
            name.equals(user.name) &&
            surname.equals(user.surname) &&
            email.equals(user.email) &&
            position.equals(user.position) &&
            language.equals(user.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, surname, email, position, language);
  }
}
