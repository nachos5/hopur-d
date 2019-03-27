package models;

import java.util.ArrayList;

/**
 * heldur utan um öll gögn sem ferð þarf
 */
public class TripModel {
  private int id;
  private String name;
  private String category;
  private int price;
  private int duration;
  private int groupSize;
  private String country;
  private String city;
  private String accessability;
  private String language;
  private Boolean sustainable;
  private Double rating;
  private String description;
  private CompanyModel company;

  private ArrayList<ReviewModel> reviews;

  // constructor þegar við ætlum að inserta í database-ið, notum enums til að staðfesta gögnin
  public TripModel(String n, Enums.Category c, int p, int d, int gs, Enums.Country co, Enums.City ci,
                   Enums.Accessability a, Enums.Language l, Boolean s, Double r, String de, CompanyModel comp) {
    name = n;
    category = Enums.resolveCategory(c);
    price = p;
    duration = d;
    groupSize = gs;
    country = Enums.resolveCountry(co);
    city = Enums.resolveCity(ci);
    accessability = Enums.resolveAccessability(a);
    language = Enums.resolveLanguage(l);
    sustainable = s;
    rating = r;
    description = de;
    company = comp;
  }

  // constructor fyrir röð sem við búum til
  public TripModel(int i, String n, String c, int p, int d, int gs, String co, String ci, String a,
                   String l, Boolean s, Double r, String de, ArrayList<ReviewModel> re) {
    id = i;
    name = n;
    category = c;
    price = p;
    duration = d;
    groupSize = gs;
    country = co;
    city = ci;
    accessability = a;
    language = l;
    sustainable = s;
    rating = r;
    description = de;
    reviews = re;
  }

  // constructor fyrir röð sem við sækjum
  public TripModel(int i, String n, String c, int p, int d, int gs, String co, String ci, String a,
                   String l, Boolean s, Double r, String de, CompanyModel comp, ArrayList<ReviewModel> re) {
    this(i, n, c, p, d, gs, co, ci, a, l, s, r, de, re);
    company = comp;
  }

  public int getId() { return id; }

  public String getName() { return name; }

  public String getCategory() { return category; }

  public int getPrice() {
    return price;
  }

  public int getDuration() { return duration; }

  public int getGroupSize() { return groupSize; }

  public String getCountry() { return country; }

  public String getCity() { return city; }

  public String getAccessability() { return accessability; }

  public String getLanguage() { return language; }

  public Boolean isSustainable() { return sustainable; }

  public Double getRating() { return rating; }

  public String getDescription() { return description; }

  public CompanyModel getCompany() { return company; }

  public ArrayList<ReviewModel> getReviews() {
    return reviews;
  }
}
