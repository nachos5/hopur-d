package database;

import models.Review;
import models.Trip;
import models.Company;
import models.Enums;

import java.sql.*;
import java.util.ArrayList;

public class TripQueries {

  /**
   * result set to a trip object
   * @param rs the result set
   * @return a trip object
   */
  private static Trip resultSetToTrip(ResultSet rs, Boolean includeCompany) throws SQLException {
    int id = rs.getInt("id");
    String name = rs.getString("name");
    String category = rs.getString("category");
    int price = rs.getInt("price");
    int duration = rs.getInt("duration");
    int groupSize = rs.getInt("groupSize");
    String country = rs.getString("country");
    String city = rs.getString("city");
    String accessability = rs.getString("accessability");
    String language = rs.getString("language");
    Boolean sustainability = rs.getBoolean("sustainable");
    String description = rs.getString("description");
    int companyId = rs.getInt("companyId");
    // sækjum öll review fyrir ferðina!
    ArrayList<Review> reviews = ReviewQueries.getReviewsForTrip(id, false);

    if (includeCompany) {
      return new Trip(id, name, category, price, duration, groupSize, country, city, accessability, language,
              sustainability, description, CompanyQueries.getCompanyById(companyId), reviews);
    } else {
      return new Trip(id, name, category, price, duration, groupSize, country, city, accessability, language,
              sustainability, description, reviews);
    }
  }

  /**
   * inserts a trip into the database
   *
   * @param trip the trip to insert
   */
  public static void insertTrip(Trip trip) {
    String sql = "INSERT INTO daytrip.trip(name,category,price,duration,groupSize,country,city,accessability," +
            "language,sustainable,description,companyId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      // Set parameters
      pstmt.setString(1, trip.getName());
      pstmt.setString(2, trip.getCategory());
      pstmt.setInt(3, trip.getPrice());
      pstmt.setInt(4, trip.getDuration());
      pstmt.setInt(5, trip.getGroupSize());
      pstmt.setString(6, trip.getCountry());
      pstmt.setString(7, trip.getCity());
      pstmt.setString(8, trip.getAccessability());
      pstmt.setString(9, trip.getLanguage());
      pstmt.setBoolean(10, trip.isSustainable());
      pstmt.setString(11, trip.getDescription());
      pstmt.setInt(12, trip.getCompany().getId());

      pstmt.executeUpdate();
      System.out.println("Trip added to database");
    } catch (SQLException e) {
      System.err.println("insertTrip() failed: " + e.getMessage());
    }
    //close();
  }

  /**
   * @return an arraylist with all the trips from the database
   */
  public static ArrayList<Trip> getAllTrips() {
    ArrayList<Trip> trips = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.trip;";

    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Trip trip = resultSetToTrip(rs, true);
        // bætum við ferðinni í listann
        trips.add(trip);
      }
    } catch (SQLException e) {
      System.err.println("getAllTrips() failed: " + e.getMessage());
    }

    return trips;
  }

  /**
   * obtains a trip from the database by id
   * @param tripId the id of the trip
   * @return a trip object
   */
  public static Trip getTripById(int tripId) {
    String sql = "SELECT * FROM daytrip.trip WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, tripId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        return resultSetToTrip(rs, true);
      }
    } catch (SQLException e) {
      System.err.println("getTripById() failed: " + e.getMessage());
    }
    return null;
  }

  public static Trip getTripByName(String name) {
    String sql = "SELECT * FROM daytrip.trip WHERE name=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, name);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        return resultSetToTrip(rs, true);
      }
    } catch (SQLException e) {
      System.err.println("getTripById() failed: " + e.getMessage());
    }
    return null;
  }

  public static ArrayList<Trip> getTripByLocation(Enums.Country country, Enums.City city) {
    String co = Enums.resolveCountry(country);
    String ci = Enums.resolveCity(city);

    String sql = "SELECT * FROM daytrip.trip" +
            "WHERE country = ?" +
            "AND city = ?";

    ArrayList<Trip> trips = new ArrayList<>();
    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, co);
      pstmt.setString(2, ci);

      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        Trip trip = resultSetToTrip(rs, false);
        trips.add(trip);
      }
    } catch (SQLException e) {
      System.err.println("getTripsByLocation() failed" + e.getMessage());
    }

    return trips;
  }
  /**
   * Nær í allar ferðir frá fyrirtæki
   * @param companyName
   * @return listi af ferðum fyrirtækisins
   */
  public static ArrayList<Trip> getTripsByCompany(String companyName) {
    Company company = CompanyQueries.getCompanyByName(companyName);
    return getTripsByCompanyId(company.getId());
  }

  /**
   * obtains all trips by a company
   * @param compId the id of the company
   * @return an arraylist with all the companies trips
   */
  public static ArrayList<Trip> getTripsByCompanyId(int compId) {
    ArrayList<Trip> trips = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.trip WHERE companyId=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, compId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        Trip trip = resultSetToTrip(rs, false);
        // bætum við ferðinni í listann
        trips.add(trip);
      }
    } catch (SQLException e) {
      System.err.println("getTripsByCompanyId() failed: " + e.getMessage());
    }
    return trips;
  }

  /**
   * Skilar lista af ferðum sem eru á ákveðnum tíma
   * @param startDate lágmarks dagsetning
   * @param endDate hámarks dagsetning( setja inn null ef það á ekki að vera hámark )
   * @return Listi af ferðum
   */
  public static ArrayList<Trip> getTripsAtTime(Timestamp startDate, Timestamp endDate) {
    ArrayList<Trip> trips = new ArrayList<>();
    String endClause = endDate != null ? " AND dateBegin <= ?" : "";

    String sql = "SELECT * FROM daytrip.trip" +
            "WHERE id IN (" +
            "SELECT * FROM daytrip.departure" +
            "WHERE dateBegin >= ?" +
            endClause +
            "AND available = TRUE)";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setTimestamp(1, startDate);
      if (endDate != null) {
        pstmt.setTimestamp(2, endDate);
      }

      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        Trip trip = resultSetToTrip(rs, false);
        trips.add(trip);
      }
    } catch (SQLException e) {
      System.err.println("getTripsByCompanyId() failed: " + e.getMessage());
    }
    return trips;
  }

  /**
   * Sækir lista af ferðum úr gagnagrunni sem passa við leitarstrenginn,
   * þ.e. leitarstrengurinn passar við nafn, lýsingu eða flokk ferðar
   * (Postgres styður bara ensku)
   * @param search leitarstrengurinn
   * @return ArrayListi með þeim ferðum sem passa við strenginn
   */
  public static ArrayList<Trip> getTripsBySearchString(String search) {
    ArrayList<Trip> trips =  new ArrayList<>();
    String sql = "SELECT * FROM daytrip.trip WHERE" +
            "to_tsvector('english', name) @@ to_tsquery('english', ?)" +
            "OR to_tsvector('english', description) @@ plainto_tsquery('english', ?)" +
            "OR to_tsvector('english', category) @@ plainto_tsquery('english', ?)";
    // Leita eftir fyrirtæki líka?
    // Örlítið vesen þar sem við höfum bara id í trip
    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, search);
      pstmt.setString(2, search);
      pstmt.setString(3, search);

      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
        trips.add(resultSetToTrip(rs, false));
      }
    } catch (SQLException e) {
      System.err.println("getTripsBySearchString() failed" + e.getMessage());
    }

    return trips;
  }

  /**
   * Sækir lista af ferðum úr gagnagrunni sem eru á verðbilinu
   * @param maxPrice hámarks verð
   * @param minPrice lágmarks verð
   * @return
   */
  public static ArrayList<Trip> getTripPriceRange(int maxPrice, int minPrice) {
    ArrayList<Trip> trips = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.trip" +
            "WHERE price > ? AND price < ?";

    try(PreparedStatement pstmt = DbMain.conn.prepareStatement((sql))) {
      pstmt.setInt(1, maxPrice);
      pstmt.setInt(2, minPrice);

      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
        trips.add(resultSetToTrip(rs, false));
      }
    } catch (SQLException e) {
      System.err.println("getTripsBySearchString() failed" + e.getMessage());
    }
    return trips;
  }

  /**
   * Sækir lista af ferðum sem passa við leitarstreng og eru á verðbilinu
   * @param maxPrice hámarks verð
   * @param minPrice lágmarks verð
   * @param search leitarstrengur
   * @return
   */
  public static ArrayList<Trip> getTripPriceSearch(int maxPrice, int minPrice, String search) {
    ArrayList<Trip> trips =  new ArrayList<>();
    String sql = "SELECT * FROM daytrip.trip WHERE" +
            "to_tsvector('english', name) @@ to_tsquery('english', ?)" +
            "OR to_tsvector('english', description) @@ plainto_tsquery('english', ?)" +
            "OR to_tsvector('english', category) @@ plainto_tsquery('english', ?)" +
            "AND price > ?" +
            "AND price < ?";
    // Leita eftir fyrirtæki líka?
    // Örlítið vesen þar sem við höfum bara id í trip
    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, search);
      pstmt.setString(2, search);
      pstmt.setString(3, search);
      pstmt.setInt(1, maxPrice);
      pstmt.setInt(2, minPrice);

      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
        trips.add(resultSetToTrip(rs, false));
      }
    } catch (SQLException e) {
      System.err.println("getTripsBySearchString() failed" + e.getMessage());
    }

    return trips;
  }

  public static void updateTrip(Trip trip) {
    String sql = "UPDATE daytrip.trip" +
            "SET " +
            "name = ?, price = ?, category = ?, " +
            "duration = ?, groupSize = ?, country = ?, " +
            "city = ?, accessability = ?, language = ?, " +
            "description = ?";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, trip.getName());
      pstmt.setInt(2, trip.getPrice());
      pstmt.setString(3, trip.getCategory());
      pstmt.setInt(4, trip.getDuration());
      pstmt.setInt(5, trip.getGroupSize());
      pstmt.setString(6, trip.getCountry());
      pstmt.setString(7, trip.getCity());
      pstmt.setString(8, trip.getAccessability());
      pstmt.setString(9, trip.getLanguage());
      pstmt.setString(10, trip.getDescription());
      pstmt.executeQuery();

    } catch (SQLException e) {
      System.err.println("getTripsBySearchString() failed" + e.getMessage());
    }
  }

  // möguleg approach: tveir ArrayList, einn fyrir gildin, annar fyrir dálka
  //                   trip object sem hefur allt nema uppfærða dálka sem null
  //                   ArrayList með key-value pörum
  // Pæling með heppilegt return value, id eða name eða öll ferðin?
  // Hvernig er best að setja saman SET col = ?,... án þess að opna fyrir SQL inj.?
  /**
   *
   * @param id id ferðar sem á að uppfæra
   * @param columns listi af dálkum sem á að uppfæra
   * @param values listi af gildum sem á að uppfæra með
   * @throws SQLException
   */
  public static void updateTrip(int id, ArrayList<String> columns, ArrayList<String> values) throws SQLException{
    if(columns.size() != values.size()) {
      throw new SQLException("Number of columns does not match number of values");
    }
    String colString = "SET ";
    for(String col :  columns) {
      colString += col + " = ?,";
    }
    // vantar sanitazion
    colString = colString.substring(0, colString.length()-1);

    String sql = "UPDATE daytrip.trip " + colString + " RETURNING id";
    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      int i = 1;
      for(String value : values) {
        pstmt.setObject(i++, value);
      }

      pstmt.executeQuery();

    } catch (SQLException e) {
      System.err.println("getTripsBySearchString() failed" + e.getMessage());
    }

  }

  /**
   * deletes a trip from the database by id
   * @param id the id of the trip
   */
  public static void deleteTripById(int id) {
    String sql = "DELETE FROM daytrip.trip WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      System.out.println("Trip " + id + " deleted");
    } catch (SQLException e) {
      System.err.println("deleteTripById() failed: " + e.getMessage());
    }
  }
}
