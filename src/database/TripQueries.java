package database;

import models.Company;
import models.Review;
import models.Trip;

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
    //connect();
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
