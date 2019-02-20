package database.dev;

import database.DbMain;

public class Schema {

  public static void run() {
    // droppum public schemanu (og öllum töflum í leiðinni!)
    DbMain.executeStatement("DROP SCHEMA public CASCADE;");
    // búum til það uppá nýtt
    DbMain.executeStatement("CREATE SCHEMA public;");
    // býr til allar töflurnar
    createTables();
  }

  // bara eitthvað til að testa, ath þarf að breyta módelum
  // og queryum líka ef breytingar eru gerðar hérna
  private static void createTables() {
    String users = "CREATE TABLE users(" + // user er reserved orð
        "id SERIAL PRIMARY KEY," +
        "name VARCHAR(32)," +
        "email VARCHAR(32) UNIQUE" +
        ");";
    String trip = "CREATE TABLE trip(" +
        "id SERIAL PRIMARY KEY," +
        "name VARCHAR(32)," +
        "price INT" +
        ");";
    String review = "CREATE TABLE review(" +
        "userEmail VARCHAR(32)," +
        "tripId INT," +
        "text VARCHAR(999)," +
        "FOREIGN KEY (userEmail) REFERENCES users(email)," +
        "FOREIGN KEY (tripId) REFERENCES trip(id)" +
        ");";

    // setjum alla strengina saman og keyrum svo statementið
    String sql = trip + users + review;
    DbMain.executeStatement(sql);
  }
}
