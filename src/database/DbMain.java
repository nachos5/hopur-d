package database;

import database.models.Trip;
import database.models.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DbMain {

  private static String db_url;
  private static String db_user;
  private static String db_password;
  private static Connection conn = null;

  public static void init() {
    local(); // production() hér ef heroku database
    connect();
    Schema.run(); // commenta út ef það á ekki að droppa núverandi schema!
    close();
  }

  /**
   * development (local database)
   */
  private static void local() {
    db_url = System.getenv("DB_URL");
    db_user = System.getenv("DB_USER");
    db_password = System.getenv("DB_PASSWORD");
  }

  /**
   * production (heroku database)
   */
  private static void production() {
    try {
      String uri = "postgres://rsugtizrdafzct:984365342b0ea220bac9a7828d0ef1caa37724daff2c891f59a9aa16758e74e4@ec2-54-75-245-94.eu-west-1.compute.amazonaws.com:5432/d9pu9h02ajef6r";
      URI db_uri = new URI(uri);

      db_url = "jdbc:postgresql://" + db_uri.getHost() + ':' + db_uri.getPort() + db_uri.getPath();
      db_user = db_uri.getUserInfo().split(":")[0];
      db_password = db_uri.getUserInfo().split(":")[1];
    } catch (URISyntaxException e) {
      System.out.println(e.getMessage());
    }
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
     // System.out.println(sql);
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
    String sql = "INSERT INTO trip(name,price) VALUES(?,?);";

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
    String sql = "SELECT * FROM trip;";

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
    String sql = "INSERT INTO users(username,email,password) VALUES(?,?,?);";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      // stillum parametra
      pstmt.setString(1, user.getName());
      pstmt.setString(2, user.getEmail());
      pstmt.setString(3, user.getPassword());
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

    //connect();
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
      while(rs.next()) {
        String username = rs.getString("username");
        String password = rs.getString("password");
        String email = rs.getString("email");
        // bætum við notandanum í listann
        users.add(new User(username, email, password));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    //close();
    return users;
  }

  /**
   *
   * @param username
   * @return
   */
  public static User getUser(String username) {
    String sql = "SELECT * FROM users WHERE username = ?";

    try {
        PreparedStatement preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setString(1, username);

        ResultSet rs = preparedStmt.executeQuery();
        rs.next();

        return new User(
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password")
        );
    } catch (SQLException e) {
      System.err.println("getUsers failed: " + e.getMessage());
    }
    return null;
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
