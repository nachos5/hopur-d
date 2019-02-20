package database.models;

/**
 * heldur utan um öll gögn sem notandi þarf
 */
public class User {
  private String name;
  private String email;

  public User(String n, String e) {
    name = n;
    email = e;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }
}
