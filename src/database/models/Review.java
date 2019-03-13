package database.models;

public class Review {

  private User user;
  private Trip trip;
  private String title;
  private String text;
  private Double rating;
  private Boolean isPublic;

  // constructor án trip
  public Review(User u, String ti, String te, Double r, Boolean p) {
    user = u;
    title = ti;
    text = te;
    rating = r;
    isPublic = p;
  }

  // constructor með trip
  public Review(User u, Trip t, String ti, String te, Double r, Boolean p) {
    this(u, ti, te, r, p);
    trip = t;
  }

  public User getUser() { return user; }

  public Trip getTrip() {
    return trip;
  }

  public String getTitle() { return title; }

  public String getText() {
    return text;
  }

  public Double getRating() { return rating; }

  public Boolean getIsPublic() { return isPublic; }
}
