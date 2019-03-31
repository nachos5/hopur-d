package models;

import java.util.ArrayList;

public class Company {
  private int id;
  private String name;
  private double rating;
  private String description;
  private ArrayList<Trip> trips;

  // constructor fyrir röð sem við búum til
  public Company(String n, double r, String d) {
    name = n;
    rating = r;
    description = d;
  }

  // constructor fyrir röð sem við sækjum
  public Company(int i, String n, double r, String d, ArrayList<Trip> t) {
    this(n, r, d);
    id = i;
    trips = t;
  }

  public int getId() { return id; }

  public String getName() { return name; }

  public double getRating() { return rating; }

  public String getDescription() { return description; }

  public ArrayList<Trip> getTrips() { return trips; }

}
