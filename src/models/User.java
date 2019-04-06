package models;

public class User {
  private int id;
  private String username;
  private Boolean admin;
  private String password;
  private String email;

  // constructor fyrir röð sem við búum til
  public User(String us, Boolean ad, String em, String pw) {
    username = us;
    admin = ad;
    email = em;
    password = pw;
  }

  // constructor fyrir röð sem við sækjum
  public User(int i, String us, Boolean ad, String em, String pw) {
    this(us, ad, em, pw);
    id = i;
  }

  public int getId() { return id; }

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
