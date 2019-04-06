package models;

import java.util.ArrayList;

public class Company {
  private int id;
  private String name;
  private String description;
  private ArrayList<Trip> trips;

  // constructor fyrir röð sem við búum til
  public Company(String n, String d) {
    name = n;
    description = d;
  }

  // constructor fyrir röð sem við sækjum
  public Company(int i, String n, String d, ArrayList<Trip> t) {
    this(n, d);
    id = i;
    trips = t;
  }

  public int getId() { return id; }

  public String getName() { return name; }

  public String getDescription() { return description; }

  public ArrayList<Trip> getTrips() { return trips; }

}
