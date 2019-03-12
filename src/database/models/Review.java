package database.models;

public class Review {

  private int userId;
  private int tripId;
  private String text;

  public Review(int uId, int tId, String t) {
    userId = uId;
    tripId = tId;
    text = t;
  }
  public int getUserId() {
    return userId;
  }

  public int getTripId() {
    return tripId;
  }

  public String getText() {
    return text;
  }
}
