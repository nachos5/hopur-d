package database;

import database.models.Company;
import database.models.Trip;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CompanyQueries {

  public static void insertCompany(Company company) {
    String sql = "INSERT INTO daytrip.company(name,rating,description) VALUES (?,?,?);";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      // Set parameters
      pstmt.setString(1, company.getName());
      pstmt.setDouble(2, company.getRating());
      pstmt.setString(3, company.getDescription());

      pstmt.executeUpdate();
      System.out.println("Company added to database");
    } catch (SQLException e) {
      System.err.println("insertCompany() failed: " + e.getMessage());
    }
  }

  public static ArrayList<Company> getAllCompanies() {
    ArrayList<Company> companies = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.company;";

    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double rating = rs.getDouble("rating");
        String description = rs.getString("description");
        ArrayList<Trip> trips = TripQueries.getTripsByCompanyId(id);

        // bætum við ferðinni í listann
        companies.add(
            new Company(id, name, rating, description, trips)
        );
      }
    } catch (SQLException e) {
      System.err.println("getAllCompanies() failed: " + e.getMessage());
    }

    return companies;
  }

  public static Company getCompanyById(int depId) {
    String sql = "SELECT * FROM daytrip.company where id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, depId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double rating = rs.getDouble("rating");
        String description = rs.getString("description");
        ArrayList<Trip> trips = TripQueries.getTripsByCompanyId(id);

        return new Company(id, name, rating, description, trips);
      }
    } catch (SQLException e) {
      System.err.println("getCompanyById() failed: " + e.getMessage());
    }

    return null;
  }

  public static Company getCompanyByName(String compName) {
    String sql = "SELECT * FROM daytrip.company where name=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setString(1, compName);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double rating = rs.getDouble("rating");
        String description = rs.getString("description");
        ArrayList<Trip> trips = TripQueries.getTripsByCompanyId(id);

        return new Company(id, name, rating, description, trips);
      }
    } catch (SQLException e) {
      System.err.println("getCompanyByName() failed: " + e.getMessage());
    }

    return null;
  }

  public static void deleteCompanyById(int id) {
    String sql = "DELETE FROM daytrip.company WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      System.out.println("Company " + id + " deleted");
    } catch (SQLException e) {
      System.err.println("deleteCompanyById() failed: " + e.getMessage());
    }
  }

}
