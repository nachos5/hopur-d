package database.models;

/**
 * heldur utan um öll gögn sem ferð þarf
 */
public class Trip {
  private String name;
  private int price;

  public Trip(String n, int p) {
    name = n;
    price = p;
  }

  public String getName() {
    return name;
  }

  public int getPrice() {
    return price;
  }
}
