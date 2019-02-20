package database.models;

import java.util.ArrayList;

/**
 * heldur utan um öll gögn sem ferð þarf
 */
public class Trip {
  private String name;
  private int price;
  private ArrayList<Review> reviews;

  // ferð sem við búum til hefur engin reviews
  public Trip(String n, int p) {
    name = n;
    price = p;
  }

  // þegar við sækjum ferð þurfum við að geyma reviews
  public Trip(String n, int p, ArrayList<Review> r) {
    name = n;
    price = p;
    reviews = r;
  }

  public String getName() {
    return name;
  }

  public int getPrice() {
    return price;
  }

  public ArrayList<Review> getReviews() {
    return reviews;
  }
}
