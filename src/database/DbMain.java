package database;

import database.dev.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbMain {

  private static String db_url;
  private static String db_user;
  private static String db_password;
  static Connection conn = null; // aðgengilegt í pakkanum

  public static void init() {
    local(); // production() hér ef heroku database
    connect();
    dev(); // commenta út ef það á ekki að droppa núverandi schema eða runna testum
  }

  /**
   * sér um að "resetta" databaseið, þarf bara í development
   */
  private static void dev() {
    // keyrum skemað til að fokka í með testunum
    Schema.run(db_user);
    Insert.run();
    // keyrum test
    Tests.run();
    // hreinsum skemað sem við notuðum fyrir testin og búum til uppá nýtt
    Schema.run(db_user);
    Insert.run();
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
      System.err.println(e.getMessage());
    }
  }

  public static void connect() {
    try {
      conn = DriverManager.getConnection(db_url, db_user, db_password);
      System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException e) {
      System.err.println("connect() failed: " + e.getMessage());
    }
  }

  public static void close() {
    try {
      conn.close();
    } catch (SQLException e) {
      System.err.println("close() failed: " + e.getMessage());
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
      System.err.println("executeStatement() failed: " + e.getMessage());
    }
  }

  /**
   * betra en að returna conn í connect svo maður þurfi ekki alltaf
   * að connecta uppá nýtt þegar maður vill nálgast conn
   */
  public static Connection getConnection() {
    return conn;
  }


}
