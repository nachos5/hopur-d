package database.models;

public class Review {

  private String userEmail;
  private int tripId;
  private String text;

  public Review(String email, int id, String t) {
    userEmail = email;
    tripId = id;
    text = t;
  }
  public String getUserEmail() {
    return userEmail;
  }

  public int getTripId() {
    return tripId;
  }

  public String getText() {
    return text;
  }
}
