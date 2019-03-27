package database;

import models.*;

import java.sql.*;
import java.util.ArrayList;

public class ReviewQueries {

  /**
   * setur inn review í databaseið með prepare-uðu statementi
   *
   * @param review ...
   */
  public static void insertReview(ReviewModel review) {
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
      System.out.println("ReviewModel added to database");
    } catch (SQLException e) {
      System.err.println("insertReview() failed:" + e.getMessage());
    }
  }

  public static ArrayList<ReviewModel> getAllReviews() {
    ArrayList<ReviewModel> reviews = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.review;";

    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String text = rs.getString("text");
        Double rating = rs.getDouble("rating");
        Boolean isPublic = rs.getBoolean("isPublic");
        String username = rs.getString("username");
        UserModel user = UserQueries.getUser(username);
        int tripId = rs.getInt("tripId");
        TripModel trip = TripQueries.getTripById(tripId);
        // bætum við ferðinni í listann
        reviews.add(
            new ReviewModel(id, user, trip, title, text, rating, isPublic)
        );
      }
    } catch (SQLException e) {
      System.err.println("getAllReviews() failed: " + e.getMessage());
    }

    return reviews;
  }

  public static ReviewModel getReviewById(int revId) {
    String sql = "SELECT * FROM daytrip.review where id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, revId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        int tripId = rs.getInt("tripId");
        String title = rs.getString("title");
        String text = rs.getString("text");
        Double rating = rs.getDouble("rating");
        Boolean isPublic = rs.getBoolean("isPublic");

        return new ReviewModel(id, UserQueries.getUser(username), TripQueries.getTripById(tripId), title, text, rating, isPublic);
      }
    } catch (SQLException e) {
      System.err.println("getReviewById() failed: " + e.getMessage());
    }

    return null;
  }

  /**
   * @param tripId referencar trip
   * @param includeTrip hvort review objectarnir eigi að innihalda TripModel, augljóslega óþarfi ef við vitum trippið
   * @return array listi með öllum reviews úr databaseinu með tilteknu trip id-i
   */
  public static ArrayList<ReviewModel> getReviewsForTrip(int tripId, Boolean includeTrip) {
    ArrayList<ReviewModel> reviews = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.review where tripId=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, tripId);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()) {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String title = rs.getString("title");
        String text = rs.getString("text");
        Double rating = rs.getDouble("rating");
        Boolean isPublic = rs.getBoolean("isPublic");
        // bætum við ferðinni í listann
        if (includeTrip) {
          reviews.add(
              new ReviewModel(id, UserQueries.getUser(username), TripQueries.getTripById(tripId), title, text, rating, isPublic)
          );
        } else {
          reviews.add(
              new ReviewModel(UserQueries.getUser(username), title, text, rating, isPublic)
          );
        }
      }
    } catch (SQLException e) {
      System.err.println("getReviewsForTrip() failed: " + e.getMessage());
    }

    return reviews;
  }

  public static void deleteReviewById(int id) {
    String sql = "DELETE FROM daytrip.review WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      System.out.println("ReviewModel " + id + " deleted");
    } catch (SQLException e) {
      System.err.println("deleteReviewById() failed: " + e.getMessage());
    }
  }

}
