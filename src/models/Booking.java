package models;

import java.util.GregorianCalendar;

public class Booking {
  private int id;
  private User user;
  private Departure departure;
  private GregorianCalendar bookedAt;
  private String status;

  // constructor fyrir röð sem við búum til
  public Booking(User u, Departure d, Enums.Status s) {
    user = u;
    departure = d;
    status = Enums.resolveStatus(s);
  }

  // constructor fyrir röð sem við sækjum
  public Booking(int i, User u, Departure d, GregorianCalendar g, String s) {
    id = i;
    user = u;
    departure = d;
    bookedAt = g;
    status = s;
  }

  public int getId() { return id; }

  public User getUser() { return user; }

  public Departure getDeparture() { return departure; }

  public GregorianCalendar getBookedAt() { return bookedAt; }

  public String getStatus() { return status; }

}