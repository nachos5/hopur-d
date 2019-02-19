package database;

import database.models.Trip;
import database.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DbMain {

  private static final String db_url = System.getenv("DATABASE_URL");
  private static final String db_user = System.getenv("DATABASE_USER");
  private static final String db_password = System.getenv("DATABASE_PASSWORD");
  private static Connection conn = null;

  public static void init() {
    connect();
    Schema.run(); // commenta út ef það á ekki að droppa núverandi schema!
    close();
  }

  public static void connect() {
    try {
      conn = DriverManager.getConnection(db_url, db_user, db_password);
      System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void close() {
    try {
      conn.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * einfalt fall til að keyra unprepared statement
   *
   * @param sql sql strengurinn
   */
   public static void executeStatement(String sql) {
    try (Statement stmt = conn.createStatement()) {
      stmt.execute(sql);
    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }
  }


  // **************************************************** //
  // ******************** FERÐIR ************************ //
  // **************************************************** //

  /**
   * setur inn ferð í databaseið með prepare-uðu statementi
   *
   * @param trip ferð
   */
  public static void insertTrip(Trip trip) {
    String sql = "INSERT INTO trips(name,price) VALUES(?,?);";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      // stillum parametra
      pstmt.setString(1, trip.getName());
      pstmt.setInt(2, trip.getPrice());
      // framkvæmum statementið
      pstmt.executeUpdate();
      System.out.println("Trip added to database");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * @return array listi með öllum ferðum úr databaseinu
   */
  public static ArrayList<Trip> getAllTrips() {
    ArrayList<Trip> trips = new ArrayList<>();
    String sql = "SELECT * FROM trips;";

    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
      while(rs.next()) {
        String name = rs.getString("name");
        int price = rs.getInt("price");
        // bætum við ferðinni í listann
        trips.add(new Trip(name, price));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return trips;
  }



  // **************************************************** //
  // ******************** NOTENDUR ********************** //
  // **************************************************** //

  /**
   * setur inn user í databaseið með prepare-uðu statementi
   *
   * @param user notandi
   */
  public static void insertUser(User user) {
    String sql = "INSERT INTO users(name,email) VALUES(?,?);";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      // stillum parametra
      pstmt.setString(1, user.getName());
      pstmt.setString(2, user.getEmail());
      // framkvæmum statementið
      pstmt.executeUpdate();
      System.out.println("User added to database");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * @return array listi með öllum userum úr databaseinu
   */
  public static ArrayList<User> getAllUsers() {
    ArrayList<User> users = new ArrayList<>();
    String sql = "SELECT * FROM users;";

    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
      while(rs.next()) {
        String name = rs.getString("name");
        String email = rs.getString("email");
        // bætum við notandanum í listann
        users.add(new User(name, email));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return users;
  }


  // **************************************************** //
  // ******************** UTILS ************************* //
  // **************************************************** //

  /**
   * betra en að returna conn í connect svo maður þurfi ekki alltaf
   * að connecta uppá nýtt þegar maður vill nálgast conn
   */
  public static Connection getConnection() {
    return conn;
  }


}
