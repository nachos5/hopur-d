package models;

import java.util.GregorianCalendar;

public class DepartureModel {
  private int id;
  private TripModel trip; // mér finnst þetta meika meiri sense heldur en að extenda
  private GregorianCalendar dateBegin;
  private Boolean available;
  private int bookings;

  // constructor fyrir röð sem við búum til
  public DepartureModel(TripModel t, GregorianCalendar d, Boolean a, int b) {
    trip = t;
    dateBegin = d;
    available = a;
    bookings = b;
  }

  // constructor fyrir röð sem við sækjum
  public DepartureModel(int i, TripModel t, GregorianCalendar d, Boolean a, int b) {
    this(t, d, a, b);
    id = i;
  }

  public int getId() { return id; }

  public TripModel getTrip() { return trip; }

  public GregorianCalendar getDateBegin() { return dateBegin; }

  public Boolean getAvailable() { return available; }

  public int getBookings() { return bookings; }
}
