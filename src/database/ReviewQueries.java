package database;

import models.Review;
import models.Trip;
import models.User;
import org.json.JSONObject;
import models.JSON.*;
import static models.JSON.*;

import java.sql.*;
import java.util.ArrayList;

public class ReviewQueries {

  private static Review resultSetToReview(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    String title = rs.getString("title");
    String text = rs.getString("text");
    Double rating = rs.getDouble("rating");
    Boolean isPublic = rs.getBoolean("isPublic");
    String username = rs.getString("username");
    User user = UserQueries.getUser(username);
    int tripId = rs.getInt("tripId");
    Trip trip = TripQueries.getTripById(tripId);
    return new Review(id, user, trip, title, text, rating, isPublic);
  }

  /**
   * setur inn review í databaseið með prepare-uðu statementi
   *
   * @param review ...
   */
  public static void insertReview(Review review) {
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
  }

  public static ArrayList<Review> dynamicReviewQuery(JSONObject obj) {
    ArrayList<Review> reviews = new ArrayList<>();
    if (obj.length() == 0) return reviews;

    ArrayList<String> conditions = new ArrayList<>();
    String orderBy = "";

    if (obj.has(resolveReview(reviewJSONenum.USER))) conditions.add("userId = " + obj.get("user"));
    if (obj.has(resolveReview(reviewJSONenum.TITLE))) conditions.add("title = " + obj.get("title"));
    if (obj.has(resolveReview(reviewJSONenum.TEXT))) conditions.add("text = " + obj.get("text"));
    if (obj.has(resolveReview(reviewJSONenum.RATING))) conditions.add("rating = " + obj.get("rating"));
    if (obj.has(resolveReview(reviewJSONenum.RATINGMIN))) conditions.add("rating > " + obj.get("ratingMin"));
    if (obj.has(resolveReview(reviewJSONenum.RATINGMAX)))  conditions.add("rating < " + obj.get("ratingMax"));
    if (obj.has(resolveReview(reviewJSONenum.ISPUBLIC))) conditions.add("isPublic = " + obj.get("isPublic"));
    if (obj.has(resolveReview(reviewJSONenum.ORDERBY))) orderBy += " order by " + obj.get("orderBy");

    String sql = "SELECT * FROM daytrip.review WHERE " + String.join(" and ", conditions) + orderBy + ";";

    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Review review = resultSetToReview(rs);
        // bætum við ferðinni í listann
        reviews.add(review);
      }
    } catch (SQLException e) {
      System.err.println("getAllReviews() failed: " + e.getMessage());
    }

    return reviews;
  }


  public static ArrayList<Review> getAllReviews() {
    ArrayList<Review> reviews = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.review;";

    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Review review = resultSetToReview(rs);
        // bætum við ferðinni í listann
        reviews.add(review);
      }
    } catch (SQLException e) {
      System.err.println("getAllReviews() failed: " + e.getMessage());
    }

    return reviews;
  }

  public static Review getReviewById(int revId) {
    String sql = "SELECT * FROM daytrip.review where id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, revId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        return resultSetToReview(rs);
      }
    } catch (SQLException e) {
      System.err.println("getReviewById() failed: " + e.getMessage());
    }

    return null;
  }

  /**
   * @param tripId referencar trip
   * @param includeTrip hvort review objectarnir eigi að innihalda Trip, augljóslega óþarfi ef við vitum trippið
   * @return array listi með öllum reviews úr databaseinu með tilteknu trip id-i
   */
  public static ArrayList<Review> getReviewsForTrip(int tripId, Boolean includeTrip) {
    ArrayList<Review> reviews = new ArrayList<>();
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
              new Review(id, UserQueries.getUser(username), TripQueries.getTripById(tripId), title, text, rating, isPublic)
          );
        } else {
          reviews.add(
              new Review(UserQueries.getUser(username), title, text, rating, isPublic)
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
      System.out.println("Review " + id + " deleted");
    } catch (SQLException e) {
      System.err.println("deleteReviewById() failed: " + e.getMessage());
    }
  }

}
