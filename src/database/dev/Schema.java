package database.dev;

//import javax.rmi.CORBA.Util;

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
        "username VARCHAR(64) UNIQUE," +
        "admin BOOLEAN," +
        "email VARCHAR(64) UNIQUE," +
        "password VARCHAR(128) UNIQUE," +
        "created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP" +
        ");");
    tables.add("CREATE TABLE daytrip.company(" +
        "id SERIAL PRIMARY KEY," +
        "name VARCHAR(64)," +
        "description TEXT," +
        "created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP" +
        ");");
    tables.add("CREATE TABLE daytrip.trip(" +
        "id SERIAL PRIMARY KEY," +
        "name VARCHAR(64)," +
        "category VARCHAR(64)," +
        "price INT," +
        "duration INT," +
        "groupSize INT," +
        "country VARCHAR(32)," +
        "city VARCHAR(32)," +
        "accessability VARCHAR(32)," +
        "language VARCHAR(32)," +
        "sustainable BOOLEAN," +
        "description TEXT," +
        "companyId int," +
        "created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP," +
        "FOREIGN KEY (companyId) REFERENCES daytrip.company(id)" +
        ");");
    tables.add("CREATE TABLE daytrip.departure(" +
        "id SERIAL PRIMARY KEY," +
        "tripId INT," +
        "dateBegin TIMESTAMP," +
        "available BOOLEAN," +
        "bookings INT," +
        "created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP," +
        "FOREIGN KEY (tripId) REFERENCES daytrip.trip(id)" +
        ");");
    tables.add("CREATE TABLE daytrip.review(" +
        "id SERIAL PRIMARY KEY," +
        "title VARCHAR(64)," +
        "text VARCHAR(999)," +
        "rating NUMERIC(2,1)," +
        "isPublic BOOLEAN," + // public er reserved í java
        "username VARCHAR(32)," +
        "tripId INT," +
        "created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP," +
        "FOREIGN KEY (username) REFERENCES daytrip.users(username)," +
        "FOREIGN KEY (tripId) REFERENCES daytrip.trip(id)" +
        ");");
    tables.add("CREATE TABLE daytrip.booking(" +
        "id SERIAL PRIMARY KEY," +
        "username VARCHAR(32)," +
        "departureId INT," +
        "bookedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "status VARCHAR(32)," +
        "created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP," +
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

