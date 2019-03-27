package models;

public class ReviewModel {

  private int id;
  private UserModel user;
  private TripModel trip;
  private String title;
  private String text;
  private Double rating;
  private Boolean isPublic;

  // constructor fyrir röð sem við búum til
  public ReviewModel(UserModel u, String ti, String te, Double r, Boolean p) {
    user = u;
    title = ti;
    text = te;
    rating = r;
    isPublic = p;
  }

  // constructor fyrir röð sem við sækjum, með id-i
  public ReviewModel(int i, UserModel u, String ti, String te, Double r, Boolean p) {
    this(u, ti, te, r, p);
    id = i;
  }

  // constructor fyrir röð sem við sækjum, með trip
  public ReviewModel(UserModel u, TripModel t, String ti, String te, Double r, Boolean p) {
    this(u, ti, te, r, p);
    trip = t;
  }

  // constructor fyrir röð sem við sækjum, með trip og id
  public ReviewModel(int i, UserModel u, TripModel t, String ti, String te, Double r, Boolean p) {
    this(u, ti, te, r, p);
    id = i;
    trip = t;
  }

  public int getId() { return id; }

  public UserModel getUser() { return user; }

  public TripModel getTrip() {
    return trip;
  }

  public String getTitle() { return title; }

  public String getText() {
    return text;
  }

  public Double getRating() { return rating; }

  public Boolean getIsPublic() { return isPublic; }
}
