package database.dev;

import javax.rmi.CORBA.Util;

import java.util.ArrayList;

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
    ArrayList<String> tables = new ArrayList<>();

    tables.add("CREATE TABLE daytrip.users(" +
        "id SERIAL PRIMARY KEY," +
        "username VARCHAR(32) UNIQUE," +
        "admin BOOLEAN," +
        "email VARCHAR(32) UNIQUE," +
        "password VARCHAR(128) UNIQUE" +
        ");");
    tables.add("CREATE TABLE daytrip.company(" +
        "id SERIAL PRIMARY KEY," +
        "name VARCHAR(49) UNIQUE," +
        "rating NUMERIC(2,1)," +
        "description TEXT" +
        ");");
    tables.add("CREATE TABLE daytrip.trip(" +
        "id SERIAL PRIMARY KEY," +
        "name VARCHAR(32)," +
        "category VARCHAR(32)," +
        "price INT," +
        "duration INT," +
        "groupSize INT," +
        "country VARCHAR(32)," +
        "city VARCHAR(32)," +
        "accessability VARCHAR(32)," +
        "language VARCHAR(32)," +
        "sustainable BOOLEAN," +
        "rating NUMERIC(2,1)," +
        "description VARCHAR(999)," +
        "companyId int," +
        "FOREIGN KEY (companyId) REFERENCES daytrip.company(id)" +
        ");");
    tables.add("CREATE TABLE daytrip.departure(" +
        "id SERIAL PRIMARY KEY," +
        "tripId INT," +
        "dateBegin TIMESTAMP," +
        "available BOOLEAN," +
        "bookings INT," +
        "FOREIGN KEY (tripId) REFERENCES daytrip.trip(id)" +
        ");");
    tables.add("CREATE TABLE daytrip.review(" +
        "id SERIAL PRIMARY KEY," +
        "title VARCHAR(49)," +
        "text VARCHAR(999)," +
        "rating NUMERIC(2,1)," +
        "isPublic BOOLEAN," + // public er reserved í java
        "username VARCHAR(32)," +
        "tripId INT," +
        "FOREIGN KEY (username) REFERENCES daytrip.users(username)," +
        "FOREIGN KEY (tripId) REFERENCES daytrip.trip(id)" +
        ");");
    tables.add("CREATE TABLE daytrip.booking(" +
        "id SERIAL PRIMARY KEY," +
        "username VARCHAR(32)," +
        "departureId INT," +
        "bookedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "status VARCHAR(32)," +
        "FOREIGN KEY (username) REFERENCES daytrip.users(username)," +
        "FOREIGN KEY (departureId) REFERENCES daytrip.departure(id)" +
        ");");

    // setjum alla strengina saman og keyrum svo statementið
    String sql = "";
    for (String t: tables) {
      sql += t;
    }
    executeStatement(sql);
  }
}

