package hopurd.models;

import java.util.ArrayList;

/**
 * heldur utan um öll gögn sem ferð þarf
 */
public class Trip {
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
  private String description;
  private Company company;

  private ArrayList<Review> reviews;

  // constructor þegar við ætlum að inserta í database-ið, notum enums til að staðfesta gögnin
  public Trip(String n, Enums.Category c, int p, int d, int gs, Enums.Country co, Enums.City ci,
              Enums.Accessability a, Enums.Language l, Boolean s, String de, Company comp) {
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
    description = de;
    company = comp;
  }

  // constructor með company og reviews
  public Trip (String n, Enums.Category c, int p, int d, int gs, Enums.Country co, Enums.City ci,
               Enums.Accessability a, Enums.Language l, Boolean s, String de, Company comp, ArrayList<Review> re) {
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
    description = de;
    company = comp;
    reviews = re;
  }

  // constructor fyrir röð sem við sækjum með reviews en án company
  public Trip(int i, String n, String c, int p, int d, int gs, String co, String ci, String a,
              String l, Boolean s, String de, ArrayList<Review> re) {
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
    description = de;
    reviews = re;
  }

  // constructor fyrir röð sem við sækjum með company og reviews
  public Trip(int i, String n, String c, int p, int d, int gs, String co, String ci, String a,
              String l, Boolean s, String de, Company comp, ArrayList<Review> re) {
    this(i, n, c, p, d, gs, co, ci, a, l, s, de, re);
    company = comp;
  }

  public void addReview(Review review) {
    this.reviews.add(review);
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

  public String getDescription() { return description; }

  public Company getCompany() { return company; }

  public ArrayList<Review> getReviews() {
    return reviews;
  }
}
