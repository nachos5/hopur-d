package database;

import database.models.Departure;
import database.models.Review;
import database.models.Trip;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ReviewQueries {

  /**
   * setur inn review í databaseið með prepare-uðu statementi
   *
   * @param review ...
   */
  public static void insertReview(Review review) {
    //connect();
    String sql = "INSERT INTO daytrip.review(username,tripId,title,text,rating,isPublic) VALUES(?,?,?,?,?,?);";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      // stillum parametra
      pstmt.setString(1, review.getUser().getUsername());
      pstmt.setInt(2, review.getTrip().getId());
      pstmt.setString(3, review.getTitle());
      pstmt.setString(4, review.getText());
      pstmt.setDouble(5, review.getRating());
      pstmt.setBoolean(6, review.getIsPublic());
      // framkvæmum statementið
      pstmt.executeUpdate();
      System.out.println("Review added to database");
    } catch (SQLException e) {
      System.err.println("insertReview() failed:" + e.getMessage());
    }
    //close();
  }

  public static ArrayList<Review> getAllReviews() {
    ArrayList<Review> departures = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.review;";

    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        int id = rs.getInt("id");
        Trip trip = TripQueries.getTripById(rs.getInt("tripId"));
        Timestamp timestamp = rs.getTimestamp("dateBegin");
        GregorianCalendar dateBegin = new GregorianCalendar();
        dateBegin.setTime(timestamp);
        Boolean available = rs.getBoolean("available");
        int bookings = rs.getInt("bookings");

        // bætum við ferðinni í listann
        departures.add(
            new Departure(id, trip, dateBegin, available, bookings)
        );
      }
    } catch (SQLException e) {
      System.err.println("getAllDepartures() failed: " + e.getMessage());
    }

    return departures;
  }

  /**
   * @param tripId referencar trip
   * @param includeTrip hvort review objectarnir eigi að innihalda Trip, augljóslega óþarfi ef við vitum trippið
   * @return array listi með öllum reviews úr databaseinu með tilteknu trip id-i
   */
  public static ArrayList<Review> getReviewsForTrip(int tripId, Boolean includeTrip) {
    //connect();
    ArrayList<Review> reviews = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.review where tripId=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, tripId);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()) {
        String username = rs.getString("username");
        String title = rs.getString("title");
        String text = rs.getString("text");
        Double rating = rs.getDouble("rating");
        Boolean isPublic = rs.getBoolean("isPublic");
        // bætum við ferðinni í listann
        if (includeTrip) {
          reviews.add(
              new Review(UserQueries.getUser(username), TripQueries.getTripById(tripId), title, text, rating, isPublic)
          );
        } else {
          reviews.add(
              new Review(UserQueries.getUser(username), title, text, rating, isPublic)
          );
        }
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }

    //close();
    return reviews;
  }

  public static void deleteReviewById(int id) {
    String sql = "DELETE FROM daytrip.review WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeQuery(sql);
    } catch (SQLException e) {
      System.err.println("deleteReviewById() failed: " + e.getMessage());
    }
  }

}
