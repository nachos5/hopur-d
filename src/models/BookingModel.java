package models;

import java.util.GregorianCalendar;

public class BookingModel {
  private int id;
  private UserModel user;
  private DepartureModel departure;
  private GregorianCalendar bookedAt;
  private String status;

  // constructor fyrir röð sem við búum til
  public BookingModel(UserModel u, DepartureModel d, Enums.Status s) {
    user = u;
    departure = d;
    status = Enums.resolveStatus(s);
  }

  // constructor fyrir röð sem við sækjum
  public BookingModel(int i, UserModel u, DepartureModel d, GregorianCalendar g, String s) {
    id = i;
    user = u;
    departure = d;
    bookedAt = g;
    status = s;
  }

  public int getId() { return id; }

  public UserModel getUser() { return user; }

  public DepartureModel getDeparture() { return departure; }

  public GregorianCalendar getBookedAt() { return bookedAt; }

  public String getStatus() { return status; }

}