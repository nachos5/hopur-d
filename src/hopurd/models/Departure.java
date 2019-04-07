package hopurd.models;

import java.util.GregorianCalendar;

public class Departure {
  private int id;
  private Trip trip; // mér finnst þetta meika meiri sense heldur en að extenda
  private GregorianCalendar dateBegin;
  private Boolean available;
  private int bookings;

  // constructor fyrir röð sem við búum til
  public Departure(Trip t, GregorianCalendar d, Boolean a, int b) {
    trip = t;
    dateBegin = d;
    available = a;
    bookings = b;
  }

  // constructor fyrir röð sem við sækjum
  public Departure(int i, Trip t, GregorianCalendar d, Boolean a, int b) {
    this(t, d, a, b);
    id = i;
  }

  public int getId() { return id; }

  public Trip getTrip() { return trip; }

  public GregorianCalendar getDateBegin() { return dateBegin; }

  public Boolean getAvailable() { return available; }

  public int getBookings() { return bookings; }
}
