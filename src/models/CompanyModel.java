package models;

import java.util.ArrayList;

public class CompanyModel {
  private int id;
  private String name;
  private double rating;
  private String description;
  private ArrayList<TripModel> trips;

  // constructor fyrir röð sem við búum til
  public CompanyModel(String n, double r, String d) {
    name = n;
    rating = r;
    description = d;
  }

  // constructor fyrir röð sem við sækjum
  public CompanyModel(int i, String n, double r, String d, ArrayList<TripModel> t) {
    this(n, r, d);
    id = i;
    trips = t;
  }

  public int getId() { return id; }

  public String getName() { return name; }

  public double getRating() { return rating; }

  public String getDescription() { return description; }

  public ArrayList<TripModel> getTrips() { return trips; }

}
