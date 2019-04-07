package hopurd.main;

import hopurd.database.*;
import hopurd.database.*;
import hopurd.models.*;
import hopurd.models.*;
import hopurd.models.JSON.*;
import static hopurd.models.JSON.*;
import static hopurd.database.ReviewQueries.*;
import static hopurd.database.DbMain.init;

import org.json.JSONObject;
import java.util.ArrayList;

public class API {

  public static void initDB() {
    init(false);
  }

  // ==== TRIP ==== //

  /**
   * Searches for trips, if a parameter should be ignored, insert an empty string for it, or 0 if it's an integer
   * for example this would return all objects (all parameters are ignored):
   * API.getTripsBySearchQueries("", "", 0, 0, 0, 0, 0, 0, "", "", "", "", "");
   * @param name name
   * @param category category
   * @param priceMin minimum price
   * @param priceMax maximum price
   * @param durationMin mininum duration (in minutes)
   * @param durationMax maximum duration (in minutes)
   * @param groupSizeMin mininum group size
   * @param groupSizeMax maximum group size
   * @param country country
   * @param city city
   * @param description description
   * @param companyName name of the company
   * @param orderBy the column to order by, for example 'created DESC'
   * @return ArrayList containing the trips
   */
  public static ArrayList<Trip> getTripsBySearchQueries(String name, String category, int priceMin, int priceMax,
                                                        int durationMin, int durationMax, int groupSizeMin, int groupSizeMax,
                                                        String country, String city, String description, String companyName,
                                                        String orderBy) {
    JSONObject tripJSON = new JSONObject();
    if (!name.equals(""))
      tripJSON.put(resolveTrip(tripJSONenum.NAME), name);
    if (!category.equals(""))
      tripJSON.put(resolveTrip(tripJSONenum.CATEGORY), category);
    if (priceMin > 0)
      tripJSON.put(resolveTrip(tripJSONenum.PRICEMIN), priceMin);
    if (priceMax > 0)
      tripJSON.put(resolveTrip(tripJSONenum.PRICEMAX), priceMax);
    if (durationMin > 0)
      tripJSON.put(resolveTrip(tripJSONenum.DURATIONMIN), durationMin);
    if (durationMax > 0)
      tripJSON.put(resolveTrip(tripJSONenum.DURATIONMAX), durationMax);
    if (groupSizeMin > 0)
      tripJSON.put(resolveTrip(tripJSONenum.GROUPSIZEMIN), groupSizeMin);
    if (groupSizeMax > 0)
      tripJSON.put(resolveTrip(tripJSONenum.GROUPSIZEMAX), groupSizeMax);
    if (!country.equals(""))
      tripJSON.put(resolveTrip(tripJSONenum.COUNTRY), country);
    if (!city.equals(""))
      tripJSON.put(resolveTrip(tripJSONenum.CITY), city);
    if (!description.equals(""))
      tripJSON.put(resolveTrip(tripJSONenum.DESCRIPTION), description);
    if (!companyName.equals(""))
      tripJSON.put(resolveTrip(tripJSONenum.COMPANYNAME), companyName);
    if (!orderBy.equals(""))
      tripJSON.put(resolveTrip(tripJSONenum.ORDERBY), orderBy);

    return TripQueries.dynamicTripQuery(tripJSON);
  }

  /**
   * Obtains all trips
   * @return ArrayList containing Trip objects
   */
  public static ArrayList<Trip> getAllTrips() {
    return TripQueries.getAllTrips();
  }

  /**
   * Obtains trips by company id
   * @param id id
   * @return ArrayList containing Trip objects
   */
  public static ArrayList<Trip> getTripsByCompanyId(int id) {
    return TripQueries.getTripsByCompanyId(id);
  }

  /**
   * Obtains a trip by id
   * @param id id
   * @return Trip object
   */
  public static Trip getTripById(int id) {
    return TripQueries.getTripById(id);
  }

  /**
   * Obtains a trip by name
   * @param name name
   * @return Trip object
   */
  public static Trip getTripByName(String name) {
    return TripQueries.getTripByName(name);
  }



  // ==== BOOKING ==== //

  /**
   * Obtains all bookings
   * @return ArrayList containing Booking objects
   */
  public static ArrayList<Booking> getAllBookings() {
    return BookingQueries.getAllBookings();
  }

  /**
   * Obtains a booking
   * @param id id
   * @return Booking object
   */
  public static Booking getBookingById(int id) {
    return BookingQueries.getBookingById(id);
  }

  /**
   * Obtains a booking by username
   * @param username username
   * @return Booking object
   */
  public static Booking getBookingByUsername(String username) {
    return BookingQueries.getBookingByUsername(username);
  }




  // ==== COMPANY ==== //

  /**
   * Obtains all companies
   * @return ArrayList containing Company objects
   */
  public static ArrayList<Company> getAllCompanies() {
    return CompanyQueries.getAllCompanies();
  }

  /**
   * Obtains a company by id
   * @param id id
   * @return Company object
   */
  public static Company getCompanyById(int id) {
    return CompanyQueries.getCompanyById(id);
  }

  /**
   * Obtains a company by name
   * @param name name
   * @return Company object
   */
  public static Company getCompanyByName(String name) {
    return CompanyQueries.getCompanyByName(name);
  }

  /**
   * Obtains all companies containing the query string in its name
   * @param query querystring
   * @return ArrayList containins Company objects
   */
  public static ArrayList<Company> getCompaniesByNameQuery(String query) {
    return CompanyQueries.getCompaniesByNameQuery(query);
  }

  /**
   * Obtains all companies containing the query string in its description
   * @param query querystring
   * @return ArrayList containins Company objects
   */
  public static ArrayList<Company> getCompaniesByDescriptionQuery(String query) {
    return CompanyQueries.getCompaniesByDescriptionQuery(query);
  }


  // ==== DEPARTURE ==== //

  /**
   * Obtains all departures
   * @return ArrayList containing Departure objects
   */
  public static ArrayList<Departure> getAllDepartures() {
    return DepartureQueries.getAllDepartures();
  }

  /**
   * Obtains a departure by id
   * @param id id
   * @return Departure object
   */
  public static Departure getDepartureById(int id) {
    return DepartureQueries.getDepartureById(id);
  }

  /**
   * Obtains all available departures
   * @return ArrayList containing Departure objects
   */
  public static ArrayList getAvailableDepartures() {
    return DepartureQueries.getAvailableDepartures();
  }


  // ==== REVIEW ==== //

  public static ArrayList<Review> getAllReviews() {
    return ReviewQueries.getAllReviews();
  }

  public static Review getReviewById(int id) {
    return ReviewQueries.getReviewById(id);
  }

  /**
   * Obtains reviews by rating range
   * @param min double value that is equal to or larger than 0.0
   * @param max double value that is equal to or less than 5.0
   * @param asc boolean for ascending or descending ordering
   * @return ArrayList containing the reviews
   */
  public static ArrayList<Review> getReviewsByRatingRange(double min, double max, boolean asc) {
    if (min < 0.0 || max > 5.0)
      throw new IllegalArgumentException("min has to be equal to or larger than 0.0 and max equal to or less than 5.0");

    JSONObject reviewJSON = new JSONObject();
    reviewJSON.put(resolveReview(reviewJSONenum.RATINGMIN), min);
    reviewJSON.put(resolveReview(reviewJSONenum.RATINGMAX), max);
    String ascOrDesc = "asc";
    if (!asc) ascOrDesc = "desc";
    reviewJSON.put(resolveReview(reviewJSONenum.ORDERBY), "rating " + ascOrDesc);

    ArrayList<Review> reviews = dynamicReviewQuery(reviewJSON);
    return reviews;
  }

  /**
   * Obtains public reviews
   * @return ArrayList containing the public reviews
   */
  public static ArrayList<Review> getPublicReviews() {
    JSONObject reviewJSON = new JSONObject();
    reviewJSON.put(resolveReview(reviewJSONenum.ISPUBLIC), "true");
    reviewJSON.put(resolveReview(reviewJSONenum.ORDERBY), "created desc");

    ArrayList<Review> reviews = dynamicReviewQuery(reviewJSON);
    return reviews;
  }


  // ==== USER ==== //

  /**
   * Obtains all users
   * @return ArrayList containing User objects
   */
  public static ArrayList<User> getAllUsers() {
    return UserQueries.getAllUsers();
  }

  /**
   * Obtains a user by username
   * @param username username
   * @return User object
   */
  public static User getUserByName(String username) {
    return UserQueries.getUser(username);
  }

  /**
   * Obtains a user by id
   * @param id id
   * @return User object
   */
  public static User getUserById(int id) {
    return UserQueries.getUserById(id);
  }

}
