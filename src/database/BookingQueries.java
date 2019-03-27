package database;

import models.BookingModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class BookingQueries {
  public static void insertBooking(BookingModel booking) {
    String sql = "INSERT INTO daytrip.booking(username,departureId,status) VALUES (?,?,?);";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      // Set parameters
      pstmt.setString(1, booking.getUser().getUsername());
      pstmt.setInt(2, booking.getDeparture().getId());
      pstmt.setString(3, booking.getStatus());

      pstmt.executeUpdate();
      System.out.println("BookingModel added to database");
    } catch (SQLException e) {
      System.err.println("insertBooking() failed: " + e.getMessage());
    }
  }

  public static ArrayList<BookingModel> getAllBookings() {
    ArrayList<BookingModel> bookings = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.booking;";

    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        int departureId = rs.getInt("departureId");
        Timestamp timestamp = rs.getTimestamp("bookedAt");
        GregorianCalendar bookedAt = new GregorianCalendar();
        bookedAt.setTime(timestamp);
        String status = rs.getString("status");

        // bætum bókuninni í listann
        bookings.add(
            new BookingModel(id, UserQueries.getUser(username), DepartureQueries.getDepartureById(departureId), bookedAt, status)
        );
      }
    } catch (SQLException e) {
      System.err.println("getAllBookings() failed: " + e.getMessage());
    }

    return bookings;
  }

  public static BookingModel getBookingById(int bookingId) {
    String sql = "SELECT * FROM daytrip.booking where id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, bookingId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        int departureId = rs.getInt("departureId");
        Timestamp timestamp = rs.getTimestamp("bookedAt");
        GregorianCalendar bookedAt = new GregorianCalendar();
        bookedAt.setTime(timestamp);
        String status = rs.getString("status");

        return new BookingModel(id, UserQueries.getUser(username), DepartureQueries.getDepartureById(departureId), bookedAt, status);
      }
    } catch (SQLException e) {
      System.err.println("getBookingById() failed: " + e.getMessage());
    }

    return null;
  }

  public static void deleteBookingById(int id) {
    String sql = "DELETE FROM daytrip.booking WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      System.out.println("BookingModel " + id + " deleted");
    } catch (SQLException e) {
      System.err.println("deleteBookingById() failed: " + e.getMessage());
    }
  }
}
