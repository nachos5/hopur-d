package hopurd.database;

import hopurd.models.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class BookingQueries {

  /**
   * result set to a booking object
   * @param rs the result set
   * @return booking object
   */
  private static Booking resultSetToBooking(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    String username = rs.getString("username");
    int departureId = rs.getInt("departureId");
    Timestamp timestamp = rs.getTimestamp("bookedAt");
    GregorianCalendar bookedAt = new GregorianCalendar();
    bookedAt.setTime(timestamp);
    String status = rs.getString("status");
    return new Booking(id, UserQueries.getUser(username), DepartureQueries.getDepartureById(departureId), bookedAt, status);
  }

  /**
   * inserts a booking into the database
   * @param booking the booking object to insert
   */
  public static void insertBooking(Booking booking) {
    String sql = "INSERT INTO daytrip.booking(username,departureId,status) VALUES (?,?,?);";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      // Set parameters
      pstmt.setString(1, booking.getUser().getUsername());
      pstmt.setInt(2, booking.getDeparture().getId());
      pstmt.setString(3, booking.getStatus());

      pstmt.executeUpdate();
      System.out.println("Booking added to database");
    } catch (SQLException e) {
      System.err.println("insertBooking() failed: " + e.getMessage());
    }
  }

  /**
   * gets all the bookings from the database
   * @return an arraylist with all the bookings
   */
  public static ArrayList<Booking> getAllBookings() {
    ArrayList<Booking> bookings = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.booking;";

    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Booking booking = resultSetToBooking(rs);
        // bætum bókuninni í listann
        bookings.add(booking);
      }
    } catch (SQLException e) {
      System.err.println("getAllBookings() failed: " + e.getMessage());
    }

    return bookings;
  }

  /**
   * obtains a booking by its id
   * @param bookingId the id of the booking
   * @return the booking object
   */
  public static Booking getBookingById(int bookingId) {
    String sql = "SELECT * FROM daytrip.booking where id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, bookingId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        return resultSetToBooking(rs);
      }
    } catch (SQLException e) {
      System.err.println("getBookingById() failed: " + e.getMessage());
    }

    return null;
  }

  public static Booking getBookingByUsername(String username) {
    String sql = "SELECT * FROM daytrip.booking where username=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, username);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        return resultSetToBooking(rs);
      }
    } catch (SQLException e) {
      System.err.println("getBookingByUsername() failed: " + e.getMessage());
    }

    return null;
  }

  /**
   * deletes a booking from the database by id
   * @param id the id of the booking to delete
   */
  public static void deleteBookingById(int id) {
    String sql = "DELETE FROM daytrip.booking WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      System.out.println("Booking " + id + " deleted");
    } catch (SQLException e) {
      System.err.println("deleteBookingById() failed: " + e.getMessage());
    }
  }
}
