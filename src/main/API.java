package main;

import database.*;
import models.*;
import models.JSON.*;
import static models.JSON.*;
import static database.ReviewQueries.*;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

public class API {

  // ==== TRIP ==== //

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

  // ==== TRIP ==== //
  public static Trip getTrip(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("id is a positive integer");
    }

    return TripQueries.getTripById(id);
  }

  public static Trip getTrip(String name) {
    if (name.length() < 1) {
      throw new IllegalArgumentException("name must be a non-empty string");
    }
    return getTripByName(name);
  }

  public static ArrayList<Trip> getTripsBySearchString(String searchString) {
    if (searchString.length() < 1) {
      throw new IllegalArgumentException("The search string must not be empty");
    }

    return TripQueries.getTripsBySearchString(searchString);
  }

  public static ArrayList<Trip> getTripsByDate(Timestamp firstDate, Timestamp lastDate) {
    return TripQueries.getTripsAtTime(firstDate, lastDate);
  }

  public static ArrayList<Trip> getTripsByDate(Timestamp firstDate) {
    return TripQueries.getTripsAtTime(firstDate);
  }

  public static ArrayList<Trip> getTripsByCompany(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("id is a positive integer");
    }

    return TripQueries.getTripsByCompanyId(id);
  }
  public static ArrayList<Trip> getTripsByCompany(String companyName) {
    if (companyName.length() < 1) {
      throw new IllegalArgumentException("CompanyName must be a non-empty string");
    }
    return TripQueries.getTripsByCompany(companyName);
  }

  public static ArrayList<Trip> getTripsByLocation(String country, String city) {
    if (country.length() < 1 || city.length() < 1) {
      throw new IllegalArgumentException("Country and city must be a non-empty strings");
    }
    return TripQueries.getTripByLocation(country, city);
  }

  private static ArrayList<String> validateJSON(JSONObject obj) {
    Iterator<String> keys = obj.keys();
    ArrayList<String> errors = new ArrayList<>();
    while(keys.hasNext()) {
      String key = keys.next();
      Object value = obj.get(key);
      if (key.equals("name")) {
        if (((String)value).length() < 1) {
          errors.add("name must be a non-empty string");
        }
      }

      if (key.equals("category")) {
        if (!(value instanceof String) || ((String)value).length() < 1) {
          errors.add("category must be a non-empty string");
        }
      }
      if (key.equals("maxPrice")) {
        if (!(value instanceof Integer) || ((int)value) < 1) {
          errors.add("maxPrice must be a positive integer");
        }
      }
      if (key.equals("minPrice")) {
        if (!(value instanceof Integer) || ((int)value) < 1) {
          errors.add("minPrice must be a positive integer");
        }
      }
      if (key.equals("maxDuration")) {
        if (!(value instanceof Integer) || ((int)value) < 1) {
          errors.add("maxDuration must be a positive integer");
        }
      }

      if (key.equals("minDuration")) {
        if (!(value instanceof Integer) || ((int)value) < 1) {
          errors.add("minDuration must be a positive integer");
        }
      }

      if (key.equals("groupSize")) {
        if (!(value instanceof Integer) || ((int)value) < 1) {
          errors.add("groupSize must be a positive integer");
        }
      }

      if (key.equals("country")) {
        if ((models.Enums.isInEnum(((String)value).toUpperCase(), models.Enums.Country.class))) {
          errors.add("country must be a valid country");
        }
      }

      if (key.equals("city")) {
        if ((models.Enums.isInEnum(((String)value).toUpperCase(), models.Enums.Country.class))) {
          errors.add("city must be a valid country");
        }
      }

      if (key.equals("accessability")) {
        if ((value).equals("true") || (value).equals("false")) {
          errors.add("accessability must be a boolean value");
        }
      }
      if (key.equals("sustainable")) {
        if ((value).equals("true") || (value).equals("false")) {
          errors.add("sustainable must be a boolean value");
        }
      }

      if (key.equals("language")) {
        if ((models.Enums.isInEnum(((String)value).toUpperCase(), models.Enums.Country.class))) {
          errors.add("language must be a valid language");
        }
      }

      if (key.equals("description")) {
        if (!(value instanceof String) || ((String)value).length() < 1) {
          errors.add("description must be a non-empty string");
        }
      }
    }
    return errors;
  }

  public static ArrayList<Trip> getTripsByJSON(JSONObject obj) {
    Iterator<String> keys = obj.keys();
    while(keys.hasNext()) {
      String key = keys.next();
      if (!models.Enums.isInEnum(key.toUpperCase(), models.JSON.tripJSONenum.class)) {
        throw new IllegalArgumentException(key + "is not a valid key");
      }
      ArrayList<String> validations = validateJSON(obj);
      if (validations.size() != 0) {
        throw new IllegalArgumentException("Validation error \n" + validations.toString());
      }
    }
    return TripQueries.dynamicReviewQuery(obj);
  }
}
