package hopurd.database;

import hopurd.models.Review;
import hopurd.models.Trip;
import org.json.JSONObject;
import hopurd.models.JSON.*;
import static hopurd.models.JSON.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

  public static ArrayList<Trip> dynamicTripQuery(JSONObject obj) {
    ArrayList<Trip> trips = new ArrayList<>();
    if (obj.length() == 0) return getAllTrips();

    ArrayList<String> conditions = new ArrayList<>();
    String orderBy = "";

    if (obj.has(resolveTrip(tripJSONenum.NAME)))
      conditions.add("name LIKE '%' || '" + obj.get(resolveTrip(tripJSONenum.NAME)) + "' || '%'");
    if (obj.has(resolveTrip(tripJSONenum.CATEGORY)))
      conditions.add("category LIKE '%' ||'" +  obj.get(resolveTrip(tripJSONenum.CATEGORY)) + "'|| '%'");
    if (obj.has(resolveTrip(tripJSONenum.PRICEMIN)))
      conditions.add("price>=" + obj.get(resolveTrip(tripJSONenum.PRICEMIN)));
    if (obj.has(resolveTrip(tripJSONenum.PRICEMAX)))
      conditions.add("price<=" + obj.get(resolveTrip(tripJSONenum.PRICEMAX)));
    if (obj.has(resolveTrip(tripJSONenum.DURATIONMIN)))
      conditions.add("duration>=" + obj.get(resolveTrip(tripJSONenum.DURATIONMIN)));
    if (obj.has(resolveTrip(tripJSONenum.DURATIONMAX)))
      conditions.add("duration<=" + obj.get(resolveTrip(tripJSONenum.DURATIONMAX)));
    if (obj.has(resolveTrip(tripJSONenum.GROUPSIZEMIN)))
      conditions.add("groupSize>=" + obj.get(resolveTrip(tripJSONenum.GROUPSIZEMIN)));
    if (obj.has(resolveTrip(tripJSONenum.GROUPSIZEMAX)))
      conditions.add("groupSize<=" + obj.get(resolveTrip(tripJSONenum.GROUPSIZEMAX)));
    if (obj.has(resolveTrip(tripJSONenum.COUNTRY)))
      conditions.add("country LIKE '%' ||'" +  obj.get(resolveTrip(tripJSONenum.COUNTRY)) + "'|| '%'");
    if (obj.has(resolveTrip(tripJSONenum.CITY)))
      conditions.add("city LIKE '%' ||'" +  obj.get(resolveTrip(tripJSONenum.CITY)) + "'|| '%'");
    //if (obj.has(resolveTrip(tripJSONenum.ACCESSABILITY)));
    //if (obj.has(resolveTrip(tripJSONenum.LANGUAGE)));
    //if (obj.has(resolveTrip(tripJSONenum.SUSTAINABLE)));
    if (obj.has(resolveTrip(tripJSONenum.DESCRIPTION)))
      conditions.add("description LIKE '%' ||" +  obj.get(resolveTrip(tripJSONenum.DESCRIPTION)) + "|| '%'");
    if (obj.has(resolveTrip(tripJSONenum.COMPANYNAME)))
      conditions.add("companyId=(SELECT Id FROM daytrip.company WHERE name='" + obj.get(resolveTrip(tripJSONenum.COMPANYNAME) + "')"));
    if (obj.has(resolveTrip(tripJSONenum.ORDERBY)))
      orderBy += " order by " + obj.get(resolveTrip(tripJSONenum.ORDERBY));

    String sql = "SELECT * FROM daytrip.trip WHERE " + String.join(" and ", conditions) + orderBy + ";";
    System.out.println(sql);
    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Trip trip = resultSetToTrip(rs, true);
        // bætum við ferðinni í listann
        trips.add(trip);
      }
    } catch (SQLException e) {
      System.err.println("dynamicTripQuery() failed: " + e.getMessage());
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

      while (rs.next()) {
        return resultSetToTrip(rs, true);
      }
    } catch (SQLException e) {
      System.err.println("getTripById() failed: " + e.getMessage());
    }
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
        // bætum við ferðinni í listann
        trips.add(trip);
      }
    } catch (SQLException e) {
      System.err.println("getTripsByCompanyId() failed: " + e.getMessage());
    }
    return trips;
  }

  /**
   * Sækir lista af ferðum úr gagnagrunni sem passa við leitarstrenginn,
   * þ.e. leitarstrengurinn passar við nafn, lýsingu eða flokk ferðar
   * (Postgres styður bara ensku)
   * @param search leitarstrengurinn
   * @return ArrayListi með þeim ferðum sem passa við strenginn
   */
  public static ArrayList<Trip> getTripsBySearchString(String search) {
    ArrayList<Trip> trips =  new ArrayList<>();
    String sql = "SELECT * FROM daytrip.trip WHERE" +
        "to_tsvector('english', name) @@ to_tsquery('english', ?)" +
        "OR to_tsvector('english', description) @@ plainto_tsquery('english', ?)" +
        "OR to_tsvector('english', category) @@ plainto_tsquery('english', ?)";
    // Leita eftir fyrirtæki líka?
    // Örlítið vesen þar sem við höfum bara id í trip
    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, search);
      pstmt.setString(2, search);
      pstmt.setString(3, search);

      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
        trips.add(resultSetToTrip(rs, false));
      }
    } catch (SQLException e) {
      System.err.println("getTripsBySearchString() failed" + e.getMessage());
    }

    return trips;
  }

  /**
   * Gets a trip from the database by name
   * @param tripName
   * @return
   */
  public static Trip getTripByName(String tripName) {
    String sql = "SELECT * FROM daytrip.trip where name=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, tripName);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        return resultSetToTrip(rs, false);
      }
    } catch (SQLException e) {
      System.err.println("getCompanyByName() failed: " + e.getMessage());
    }

    return null;
  }

  public static void updateTripByTrip(Trip updatedTrip) {
      String sql = "UPDATE daytrip.trip " +
              "SET name = ?, category = ?, price = ?, duration = ?, groupSize = ?, country = ?," +
              "city = ?, accessability = ?, language = ?, sustainable = ?, description = ?, companyId = ? " +
              "WHERE id = ?";
      Trip trip = getTripByName(updatedTrip.getName());

      try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
          // Set parameters
          pstmt.setString(1, updatedTrip.getName());
          pstmt.setString(2, updatedTrip.getCategory());
          pstmt.setInt(3, updatedTrip.getPrice());
          pstmt.setInt(4, updatedTrip.getDuration());
          pstmt.setInt(5, updatedTrip.getGroupSize());
          pstmt.setString(6, updatedTrip.getCountry());
          pstmt.setString(7, updatedTrip.getCity());
          pstmt.setString(8, updatedTrip.getAccessability());
          pstmt.setString(9, updatedTrip.getLanguage());
          pstmt.setBoolean(10, updatedTrip.isSustainable());
          pstmt.setString(11, updatedTrip.getDescription());
          pstmt.setInt(12, updatedTrip.getCompany().getId());
          pstmt.setInt(13, trip.getId()); // Trip id to update

          pstmt.executeUpdate();
          System.out.println("Trip updated");
      } catch (SQLException e) {
          System.err.println("updateTripByTrip() failed: " + e.getMessage());
      }
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
