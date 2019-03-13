package database.models;

public class Review {

  private int id;
  private User user;
  private Trip trip;
  private String title;
  private String text;
  private Double rating;
  private Boolean isPublic;

  // constructor fyrir röð sem við búum til
  public Review(User u, String ti, String te, Double r, Boolean p) {
    user = u;
    title = ti;
    text = te;
    rating = r;
    isPublic = p;
  }

  // constructor fyrir röð sem við sækjum, með id-i
  public Review(int i, User u, String ti, String te, Double r, Boolean p) {
    this(u, ti, te, r, p);
    id = i;
  }

  // constructor fyrir röð sem við sækjum, með trip
  public Review(User u, Trip t, String ti, String te, Double r, Boolean p) {
    this(u, ti, te, r, p);
    trip = t;
  }

  // constructor fyrir röð sem við sækjum, með trip og id
  public Review(int i, User u, Trip t, String ti, String te, Double r, Boolean p) {
    this(u, ti, te, r, p);
    id = i;
    trip = t;
  }

  public int getId() { return id; }

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
