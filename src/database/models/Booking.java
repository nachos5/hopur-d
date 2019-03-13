package database.models;

import java.util.GregorianCalendar;

public class Booking {
  private int id;
  private User user;
  private Departure departure;
  private GregorianCalendar bookedAt;
  private String status;

  public Booking(User u, Departure d, GregorianCalendar g, Enums.Status s) {
    user = u;
    departure = d;
    bookedAt = g;
    status = Enums.resolveStatus(s);
  }

  public Booking(int i, User u, Departure d, GregorianCalendar g, Enums.Status s) {
    this(u, d, g, s);
    id = i;
  }
}