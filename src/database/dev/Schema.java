package database.dev;

import javax.rmi.CORBA.Util;

import static database.DbMain.*;

public class Schema {

  public static void run(String user) {
    executeStatement("DROP SCHEMA IF EXISTS daytrip CASCADE;");
    String createSchema = "CREATE SCHEMA daytrip AUTHORIZATION " + user + ";";
    executeStatement(createSchema);
    // býr til allar töflurnar
    createTables();
  }

  // bara eitthvað til að testa, ath þarf að breyta módelum
  // og queryum líka ef breytingar eru gerðar hérna
  private static void createTables() {
    String users = "CREATE TABLE daytrip.users(" +
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
    String review = "CREATE TABLE daytrip.review(" +
        "userId INT," +
        "tripId INT," +
        "text VARCHAR(999)," +
        "FOREIGN KEY (userId) REFERENCES daytrip.users(id)," +
        "FOREIGN KEY (tripId) REFERENCES daytrip.trip(id)" +
        ");";

    // setjum alla strengina saman og keyrum svo statementið
    String sql = users + trip + review;
    executeStatement(sql);
  }
}

