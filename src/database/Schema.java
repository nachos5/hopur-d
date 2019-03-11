package database;

import database.models.Trip;
import database.models.User;
import main.utilities.Utils;

import javax.rmi.CORBA.Util;

import static database.DbMain.insertTrip;
import static database.DbMain.insertUser;
import static main.utilities.Utils.delay;

public class Schema {

  public static void run(String user) {
    // droppum public schemanu (og öllum töflum í leiðinni!)
    DbMain.executeStatement("DROP SCHEMA IF EXISTS daytrip CASCADE;");
    // búum til það uppá nýtt
    String createSchema = "CREATE SCHEMA daytrip AUTHORIZATION " + user + ";";
    DbMain.executeStatement(createSchema);
    // býr til allar töflurnar
    createTables();
    // upphafs-ferðagögn
    createInitialTrips();
    // upphafs-notendagögn
    createInitialUsers();
  }

  // bara eitthvað til að testa, ath þarf að breyta módelum
  // og queryum líka ef breytingar eru gerðar hérna
  private static void createTables() {
    String users = "CREATE TABLE daytrip.users(" + // user er reserved orð
        "id SERIAL PRIMARY KEY," +
        "username VARCHAR(32)," +
        "admin BOOLEAN," +
        "email VARCHAR(32) UNIQUE," +
        "password VARCHAR(128)" +
        ");";
    String trip = "CREATE TABLE daytrip.trip(" +
        "id SERIAL PRIMARY KEY," +
        "name VARCHAR(32)," +
        "price INT" +
        ");";

    System.out.println(users);
    // setjum alla strengina saman og keyrum svo statementið
    String sql = trip + users;
    DbMain.executeStatement(sql);
  }

  private static void createInitialTrips() {
    Trip trip1 = new Trip("Fjallganga", 100);
    Trip trip2 = new Trip("Jöklaferð", 200);
    Trip trip3 = new Trip("Rútuferð", 300);

    Trip[] trips = {
        trip1, trip2, trip3
    };

    // setjum allar ferðirnar í database-ið
    for (Trip t: trips) {
      delay(100); // bíðum smá á milli requesta
      insertTrip(t);
      delay(100);
    }
  }

  private static void createInitialUsers() {
    User user1 = new User("admin", true, "admin@gmail.com", Utils.hashPassword("admin"));
    User user2 = new User("user", false,"user@gmail.com", Utils.hashPassword("siggi"));
    User user3 = new User("Bubbi", false, "bubbi@gmail.com", Utils.hashPassword("bubbi"));

    User[] users = {
        user1, user2, user3
    };

    for (User user: users) {
      delay(100);
      insertUser(user);
      delay(100);
    }
  }

}
