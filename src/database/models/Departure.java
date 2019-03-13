package database.models;

import java.util.GregorianCalendar;

public class Departure {
  private int id;
  private Trip trip; // mér finnst þetta meika meiri sense heldur en að extenda
  private GregorianCalendar dateBegin;
  private Boolean available;
  private int bookings;

  public Departure(Trip t, GregorianCalendar d, Boolean a, int b) {
    trip = t;
    dateBegin = d;
    available = a;
    bookings = b;
  }

  // þegar við sækjum hlut er gott að hafa vísun á trip objectinn sem þessi object tilheyrir
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
