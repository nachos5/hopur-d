package database.models;

/**
 * heldur utan um öll gögn sem notandi þarf
 */
public class User {
  private String username;
  private Boolean admin;
  private String password;
  private String email;

  /**
   *
   * @param us - usename
   * @param ad - admin
   * @param em - email
   * @param pw - password (hashed)
   */
  public User(String us, Boolean ad, String em, String pw) {
    username = us;
    admin = ad;
    email = em;
    password = pw;
  }

  public String getUsername() {
    return username;
  }

  public Boolean isAdmin() {
    return admin;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
