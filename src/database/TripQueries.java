package database;

import database.models.Review;
import database.models.Trip;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TripQueries {
  /**
   * setur inn ferð í databaseið með prepare-uðu statementi
   *
   * @param trip ferð
   */
  public static void insertTrip(Trip trip) {
    String sql = "INSERT INTO daytrip.trip(name,category,price,duration,groupSize,country,city,accessability," +
        "language,sustainable,rating,description,companyId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";

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
      pstmt.setDouble(11, trip.getRating());
      pstmt.setString(12, trip.getDescription());
      pstmt.setInt(13, trip.getCompany().getId());

      pstmt.executeUpdate();
      System.out.println("Trip added to database");
    } catch (SQLException e) {
      System.err.println("insertTrip() failed: " + e.getMessage());
    }
    //close();
  }

  /**
   * @return array listi með öllum ferðum úr databaseinu
   */
  public static ArrayList<Trip> getAllTrips() {
    //connect();
    ArrayList<Trip> trips = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.trip;";

    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
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
        Double rating = rs.getDouble("rating");
        String description = rs.getString("description");
        int companyId = rs.getInt("companyId");
        // sækjum öll review fyrir ferðina!
        ArrayList<Review> reviews = ReviewQueries.getReviewsForTrip(id, false);
        // bætum við ferðinni í listann
        trips.add(
            new Trip(id, name, category, price, duration, groupSize, country, city, accessability, language,
                sustainability, rating, description, CompanyQueries.getCompanyById(companyId), reviews)
        );
      }
    } catch (SQLException e) {
      System.err.println("getAllTrips() failed: " + e.getMessage());
    }

    return trips;
  }

  /**
   * Nær í ferð úr database-inu eftir id-i
   * @param tripId id ferðarinnar
   * @return Trip object
   */
  public static Trip getTripById(int tripId) {
    String sql = "SELECT * FROM daytrip.trip WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, tripId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
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
        Double rating = rs.getDouble("rating");
        String description = rs.getString("description");
        int companyId = rs.getInt("companyId");
        // sækjum öll review fyrir ferðina!
        ArrayList<Review> reviews = ReviewQueries.getReviewsForTrip(id, false);
        // bætum við ferðinni í listann
        return new Trip(id, name, category, price, duration, groupSize, country, city, accessability, language,
            sustainability, rating, description, CompanyQueries.getCompanyById(companyId), reviews);
      }
    } catch (SQLException e) {
      System.err.println("getTripById() failed: " + e.getMessage());
    }
    return null;
  }

  /**
   * Sækir ferðir úr gagnagrunninum eftir fyrirtækja id-i
   * @param compId id fyrirtækisins
   * @return ArrayListi með öllum ferðum sem innihalda þetta fyrirtækja id
   */
  public static ArrayList<Trip> getTripsByCompanyId(int compId) {
    ArrayList<Trip> trips = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.trip WHERE companyId=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, compId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
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
        Double rating = rs.getDouble("rating");
        String description = rs.getString("description");
        // sækjum öll review fyrir ferðina!
        ArrayList<Review> reviews = ReviewQueries.getReviewsForTrip(id, false);
        // bætum við ferðinni í listann
        trips.add(new Trip(id, name, category, price, duration, groupSize, country, city, accessability, language,
            sustainability, rating, description, reviews));
      }
    } catch (SQLException e) {
      System.err.println("getTripsByCompanyId() failed: " + e.getMessage());
    }
    return trips;
  }

  /**
   * Eyðir ferð úr gagnagrunninum eftir id-i
   * @param id gildi ferðarinnar
   */
  public static void deleteTripById(int id) {
    String sql = "DELETE FROM daytrip.trip WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeQuery(sql);
    } catch (SQLException e) {
      System.err.println("deleteTripById() failed: " + e.getMessage());
    }
  }
}
