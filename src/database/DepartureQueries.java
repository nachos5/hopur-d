package database;

import models.Departure;
import models.Trip;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DepartureQueries {

  private static Departure resultSetToDeparture(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    Trip trip = TripQueries.getTripById(rs.getInt("tripId"));
    Timestamp timestamp = rs.getTimestamp("dateBegin");
    GregorianCalendar dateBegin = new GregorianCalendar();
    dateBegin.setTime(timestamp);
    Boolean available = rs.getBoolean("available");
    int bookings = rs.getInt("bookings");
    return new Departure(id, trip, dateBegin, available, bookings);
  }

  /**
   * setur inn departure í databaseið með prepare-uðu statementi
   *
   * @param departure tilvik af ferð
   */
  public static void insertDeparture(Departure departure) {
    String sql = "INSERT INTO daytrip.departure(tripId,dateBegin,available,bookings) VALUES(?,?,?,?);";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      // Set parameters
      pstmt.setInt(1, departure.getTrip().getId());
      pstmt.setTimestamp(2, new Timestamp(departure.getDateBegin().getTimeInMillis()));
      pstmt.setBoolean(3, departure.getAvailable());
      pstmt.setInt(4, departure.getBookings());

      pstmt.executeUpdate();
      System.out.println("Departure added to database");
    } catch (SQLException e) {
      System.err.println("insertDeparture() failed: " + e.getMessage());
    }
  }

  /**
   * @return array listi með öllum departures úr databaseinu
   */
  public static ArrayList<Departure> getAllDepartures() {
    ArrayList<Departure> departures = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.departure;";

    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Departure departure = resultSetToDeparture(rs);
        // bætum við ferðinni í listann
        departures.add(departure);
      }
    } catch (SQLException e) {
      System.err.println("getAllDepartures() failed: " + e.getMessage());
    }

    return departures;
  }

  public static Departure getDepartureById(int depId) {
    String sql = "SELECT * FROM daytrip.departure where id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, depId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        return resultSetToDeparture(rs);
      }
    } catch (SQLException e) {
      System.err.println("getDepartureById() failed: " + e.getMessage());
    }

    return null;
  }

  public static void deleteDepartureById(int id) {
    String sql = "DELETE FROM daytrip.departure WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      System.out.println("Departure " + id + " deleted");
    } catch (SQLException e) {
      System.err.println("deleteDepartureById() failed: " + e.getMessage());
    }
  }

}
