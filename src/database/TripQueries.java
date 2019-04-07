package database;

import models.Review;
import models.Trip;
import models.Company;
import models.JSON.*;
import static models.JSON.*;

import java.sql.*;
import java.util.ArrayList;
import org.json.JSONObject;


public class TripQueries {

  /**
   * result set to a trip object
   * @param rs the result set
   * @return a trip object
   */
  private static Trip resultSetToTrip(ResultSet rs, Boolean includeCompany) throws SQLException {
    int id = rs.getInt("id");
    String name = rs.getString("name");
    String category = rs.getString("category");
    int price = rs.getInt("price");
    int duration = rs.getInt("duration");
    int groupSize = rs.getInt("groupSize");
    String country = rs.getString("country");
    String city = rs.getString("city");
    String accessability = rs.getString("accessability");
    String language = rs.getString("language");
    Boolean sustainability = rs.getBoolean("sustainable");
    String description = rs.getString("description");
    int companyId = rs.getInt("companyId");
    // sækjum öll review fyrir ferðina!
    ArrayList<Review> reviews = ReviewQueries.getReviewsForTrip(id, false);

    if (includeCompany) {
      return new Trip(id, name, category, price, duration, groupSize, country, city, accessability, language,
              sustainability, description, CompanyQueries.getCompanyById(companyId), reviews);
    } else {
      return new Trip(id, name, category, price, duration, groupSize, country, city, accessability, language,
              sustainability, description, reviews);
    }
  }

  /**
   * inserts a trip into the database
   *
   * @param trip the trip to insert
   */
  public static void insertTrip(Trip trip) {
    String sql = "INSERT INTO daytrip.trip(name,category,price,duration,groupSize,country,city,accessability," +
            "language,sustainable,description,companyId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      // Set parameters
      pstmt.setString(1, trip.getName());
      pstmt.setString(2, trip.getCategory());
      pstmt.setInt(3, trip.getPrice());
      pstmt.setInt(4, trip.getDuration());
      pstmt.setInt(5, trip.getGroupSize());
      pstmt.setString(6, trip.getCountry());
      pstmt.setString(7, trip.getCity());
      pstmt.setString(8, trip.getAccessability());
      pstmt.setString(9, trip.getLanguage());
      pstmt.setBoolean(10, trip.isSustainable());
      pstmt.setString(11, trip.getDescription());
      pstmt.setInt(12, trip.getCompany().getId());

      pstmt.executeUpdate();
      System.out.println("Trip added to database");
    } catch (SQLException e) {
      System.err.println("insertTrip() failed: " + e.getMessage());
    }
    //close();
  }

  /**
   * @return an arraylist with all the trips from the database
   */
  public static ArrayList<Trip> getAllTrips() {
    ArrayList<Trip> trips = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.trip;";

    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Trip trip = resultSetToTrip(rs, true);
        // bætum við ferðinni í listann
        trips.add(trip);
      }
    } catch (SQLException e) {
      System.err.println("getAllTrips() failed: " + e.getMessage());
    }

    return trips;
  }

  /**
   * obtains a trip from the database by id
   * @param tripId the id of the trip
   * @return a trip object
   */
  public static Trip getTripById(int tripId) {
    String sql = "SELECT * FROM daytrip.trip WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, tripId);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        return resultSetToTrip(rs, true);
      }
    } catch (SQLException e) {
      System.err.println("getTripById() failed: " + e.getMessage());
    }
    return null;
  }

  public static Trip getTripByName(String name) {
    String sql = "SELECT * FROM daytrip.trip WHERE name=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, name);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        return resultSetToTrip(rs, true);
      }
    } catch (SQLException e) {
      System.err.println("getTripById() failed: " + e.getMessage());
    }
    return null;
  }

  public static ArrayList<Trip> getTripByLocation(String country, String city) {
    ArrayList<Trip> trips = new ArrayList<>();

    if (
      !models.Enums.isInEnum(country, models.JSON.tripJSONenum.class) ||
      !models.Enums.isInEnum(country, models.JSON.tripJSONenum.class)
    ) {
      return trips;
    }

    String sql = "SELECT * FROM daytrip.trip" +
            "WHERE country = ?" +
            "AND city = ?";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, country);
      pstmt.setString(2, city);

      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        Trip trip = resultSetToTrip(rs, true);
        trips.add(trip);
      }
    } catch (SQLException e) {
      System.err.println("getTripsByLocation() failed" + e.getMessage());
    }

    return trips;
  }

  /**
   * Obtains all trips from the given company
   * @param companyName
   * @return An ArrayList of the companies trips, null if no company
   * of that name was found
   */
  public static ArrayList<Trip> getTripsByCompany(String companyName) {
    Company company = CompanyQueries.getCompanyByName(companyName);
    if (company != null)
      return getTripsByCompanyId(company.getId());
    return null;
  }

  /**
   * obtains all trips by a company
   * @param compId the id of the company
   * @return an arraylist with all the companies trips
   */
  public static ArrayList<Trip> getTripsByCompanyId(int compId) {
    ArrayList<Trip> trips = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.trip WHERE companyId=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, compId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        Trip trip = resultSetToTrip(rs, false);
        trips.add(trip);
      }
    } catch (SQLException e) {
      System.err.println("getTripsByCompanyId() failed: " + e.getMessage());
    }
    return trips;
  }

  /**
   * Gets all trips within a certain time range
   * @param startDate
   * @param endDate
   * @return An ArrayList of the trips that meet the criteria
   */
  public static ArrayList<Trip> getTripsAtTime(Timestamp startDate, Timestamp endDate) {
    ArrayList<Trip> trips = new ArrayList<>();
    String endClause = endDate != null ? " AND dateBegin <= ?" : "";

    String sql = "SELECT * FROM daytrip.trip" +
            "WHERE id IN (" +
            "SELECT * FROM daytrip.departure" +
            "WHERE dateBegin >= ?" +
            endClause +
            "AND available = TRUE)";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setTimestamp(1, startDate);
      if (endDate != null) {
        pstmt.setTimestamp(2, endDate);
      }

      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        Trip trip = resultSetToTrip(rs, true);
        trips.add(trip);
      }
    } catch (SQLException e) {
      System.err.println("getTripsByCompanyId() failed: " + e.getMessage());
    }
    return trips;
  }

  /**
   * Gets all trips that start after a certain time
   * @param startDate
   * @return An ArrayList of the trips that meet the criteria
   */
  public static ArrayList<Trip> getTripsAtTime(Timestamp startDate) {
    return getTripsAtTime(startDate, null);
  }

  /**
   * Gets a list of trips who's title or description match the search string
   * @param searchString
   * @return ArrayList of the matching trips
   */
  public static ArrayList<Trip> getTripsBySearchString(String searchString) {
    ArrayList<Trip> trips =  new ArrayList<>();
    String sql = "SELECT * FROM daytrip.trip WHERE" +
            "to_tsvector('english', name) @@ to_tsquery('english', ?)" +
            "OR to_tsvector('english', description) @@ plainto_tsquery('english', ?)" +
            "OR to_tsvector('english', category) @@ plainto_tsquery('english', ?)";

    String splicedSearchString = searchString.replaceAll(" ", "_");
    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, splicedSearchString);
      pstmt.setString(2, splicedSearchString);
      pstmt.setString(3, splicedSearchString);

      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
        trips.add(resultSetToTrip(rs, true));
      }
    } catch (SQLException e) {
      System.err.println("getTripsBySearchString() failed" + e.getMessage());
    }

    return trips;
  }

  /**
   * Gets a list of trips within the pricerange
   * @param maxPrice
   * @param minPrice
   * @return ArrayList of the trips within the pricerange
   */
  public static ArrayList<Trip> getTripPriceRange(int maxPrice, int minPrice) {
    ArrayList<Trip> trips = new ArrayList<>();
    String whereClause = minPrice == 0 ? "WHERE price <= ?" : "WHERE price <= ? AND price => ?";
    String sql = "SELECT * FROM daytrip.trip" + whereClause;

    try(PreparedStatement pstmt = DbMain.conn.prepareStatement((sql))) {
      pstmt.setInt(1, maxPrice);

      if (minPrice != 0)
        pstmt.setInt(2, minPrice);

      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
        trips.add(resultSetToTrip(rs, true));
      }
    } catch (SQLException e) {
      System.err.println("getTripsBySearchString() failed" + e.getMessage());
    }
    return trips;
  }

  /**
   * Gets a list of trips cheaper then the given price
   * @param price
   * @return ArrayList of the trips
   */
  public static ArrayList<Trip> getTripPriceRange(int price) {
    return getTripPriceRange(price, 0);
  }

  public static void updateTrip(Trip trip) {
    String sql = "UPDATE daytrip.trip" +
            "SET " +
            "name = ?, price = ?, category = ?, " +
            "duration = ?, groupSize = ?, country = ?, " +
            "city = ?, accessability = ?, language = ?, " +
            "description = ?";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, trip.getName());
      pstmt.setInt(2, trip.getPrice());
      pstmt.setString(3, trip.getCategory());
      pstmt.setInt(4, trip.getDuration());
      pstmt.setInt(5, trip.getGroupSize());
      pstmt.setString(6, trip.getCountry());
      pstmt.setString(7, trip.getCity());
      pstmt.setString(8, trip.getAccessability());
      pstmt.setString(9, trip.getLanguage());
      pstmt.setString(10, trip.getDescription());
      pstmt.executeQuery();

    } catch (SQLException e) {
      System.err.println("getTripsBySearchString() failed" + e.getMessage());
    }
  }

  /**
   *
   * @param id id ferðar sem á að uppfæra
   * @param columns listi af dálkum sem á að uppfæra
   * @param values listi af gildum sem á að uppfæra með
   * @throws SQLException
   */
  public static void updateTrip(int id, ArrayList<String> columns, ArrayList<String> values) throws SQLException{
    if(columns.size() != values.size()) {
      throw new SQLException("Number of columns does not match number of values");
    }
    String colString = "SET ";
    for(String col :  columns) {
      colString += col + " = ?,";
    }

    colString = colString.substring(0, colString.length()-1);

    String sql = "UPDATE daytrip.trip " + colString + " RETURNING id";
    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      int i = 1;
      for(String value : values) {
        pstmt.setObject(i++, value);
      }

      pstmt.executeQuery();

    } catch (SQLException e) {
      System.err.println("getTripsBySearchString() failed" + e.getMessage());
    }

  }

  /**
   * Constructs a search query dynamicly based on a JSON object
   * @param obj JSON
   * @return ArrayList of matching trips
   */
  public static ArrayList<Trip> dynamicReviewQuery(JSONObject obj) {
    ArrayList<Trip> trips = new ArrayList<>();
    if (obj.length() == 0) return trips;

    ArrayList<String> conditions = new ArrayList<>();
    ArrayList<Object> values = new ArrayList<>();
    String orderBy = "ORDER BY name";

    if (obj.has(resolveTrip(tripJSONenum.NAME))) {
      conditions.add("name like %?%");
      values.add(obj.get("name"));
    }

    if (obj.has(resolveTrip(tripJSONenum.CATEGORY))) {
      conditions.add("category = ?");
      values.add(obj.get("category"));
    }

    if (obj.has(resolveTrip(tripJSONenum.MAXPRICE))) {
      conditions.add("price <= ?");
      values.add(obj.get("maxPrice"));
    }
    if (obj.has(resolveTrip(tripJSONenum.MINPRICE))) {
      conditions.add("price > ?");
      values.add(obj.get("minPrice"));
    }

    if (obj.has(resolveTrip(tripJSONenum.MAXDURATION))) {
      conditions.add("duration <= ?");
      values.add(obj.get("maxDuration"));
    }

    if (obj.has(resolveTrip(tripJSONenum.MINDURATION))) {
      conditions.add("duration > ?");
      values.add(obj.get("minDuration"));
    }

    if (obj.has(resolveTrip(tripJSONenum.GROUPSIZE))) {
      conditions.add("groupSize <= ?");
      values.add(obj.get("groupSize"));
    }

    if (obj.has(resolveTrip(tripJSONenum.COUNTRY))) {
      conditions.add("country = ?");
      values.add(obj.get("country"));
    }

    if (obj.has(resolveTrip(tripJSONenum.CITY))) {
      conditions.add("city = ?");
      values.add(obj.get("city"));
    }

    if (obj.has(resolveTrip(tripJSONenum.ACCESSABILITY))) {
      conditions.add("accessability = ?");
      values.add(obj.get("accessability"));
    }

    if (obj.has(resolveTrip(tripJSONenum.LANGUAGE))) {
      conditions.add("language = ?");
      values.add(obj.get("language"));
    }

    if (obj.has(resolveTrip(tripJSONenum.SUSTAINABLE))) {
      conditions.add("sustainable = ?");
      values.add(obj.get("sustainable"));
    }

    if (obj.has(resolveTrip(tripJSONenum.DESCRIPTION))) {
      String splicedString = ((String) obj.get("isPublic")).replaceAll(" ", "_");
      conditions.add(
              "to_tsvector('english', description) @@ plainto_tsquery('english', ?)");
      values.add(splicedString);
    }

    if (obj.has(resolveTrip(tripJSONenum.COMPANYID))) {
      conditions.add("companyId = ?");
      values.add(obj.get("companyId"));
    }

    if (obj.has(resolveTrip(tripJSONenum.STARTDATE))) {
      String endClause = "";
      if (obj.has(resolveTrip(tripJSONenum.ENDDATE))) {
        endClause = " AND dateBegin <= ?";
      }
      String dateString = "id IN (" +
              "SELECT * FROM daytrip.departure" +
                      "WHERE dateBegin >= ?" +
                      endClause +
                      "AND available = TRUE)";
    }

    if(obj.has("orderBy")) {
      if(models.Enums.isInEnum(((String)obj.get("orderBy")).toUpperCase(), models.JSON.tripJSONenum.class)) {
        orderBy = "ORDER BY ?";
        values.add(obj.get("orderBy"));
      }
    }

    String sql = "SELECT * FROM daytrip.trip WHERE " + String.join(" and ", conditions) + orderBy + ";";

    try (PreparedStatement stmt = DbMain.conn.prepareStatement(sql)) {
      int i = 1;
      for(Object value : values) {
        stmt.setObject(i++, value);
      }

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        Trip trip = resultSetToTrip(rs, true);
        trips.add(trip);
      }
    } catch (SQLException e) {
      System.err.println("getAllReviews() failed: " + e.getMessage());
    }

    return trips;
  }

  /**
   * deletes a trip from the database by id
   * @param id the id of the trip
   */
  public static void deleteTripById(int id) {
    String sql = "DELETE FROM daytrip.trip WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      System.out.println("Trip " + id + " deleted");
    } catch (SQLException e) {
      System.err.println("deleteTripById() failed: " + e.getMessage());
    }
  }

}
