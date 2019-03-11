package database.models;

/**
 * heldur utan um öll gögn sem notandi þarf
 */
public class User {
  private String username;
  private String password;
  private String email;

  public User(String n, String e, String p) {
    username = n;
    email = e;
    password = p;
  }

  public String getName() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
