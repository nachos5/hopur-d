package database;

import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserQueries {

  private static User resultSetToUser(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    String username = rs.getString("username");
    Boolean admin = rs.getBoolean("admin");
    String email = rs.getString("email");
    String password = rs.getString("password");
    return new User(id, username, admin, email, password);
  }

  public static void insertUser(User user) {
    String sql = "INSERT INTO daytrip.users(username,admin,email,password) VALUES(?,?,?,?);";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      // stillum parametra
      pstmt.setString(1, user.getUsername());
      pstmt.setBoolean(2, user.isAdmin());
      pstmt.setString(3, user.getEmail());
      pstmt.setString(4, user.getPassword());
      // framkvæmum statementið
      pstmt.executeUpdate();
      System.out.println("User added to database");
    } catch (SQLException e) {
      System.err.println("insertUser() failed:" + e.getMessage());
    }
    //close();
  }

  /**
   *
   * @param username
   * @return
   */
  public static User getUser(String username) {
    String sql = "SELECT * FROM daytrip.users WHERE username = ?";

    try {
      PreparedStatement pstmt = DbMain.conn.prepareStatement(sql);
      pstmt.setString(1, username);

      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        return resultSetToUser(rs);
      }
    } catch (SQLException e) {
      System.err.println("getUser() failed: " + e.getMessage());
    }
    return null;
  }


  public static ArrayList<User> getAllUsers() {
    ArrayList<User> users = new ArrayList<>();
    String sql = "SELECT * FROM daytrip.users;";

    //connect();
    try (Statement stmt = DbMain.conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
      while(rs.next()) {
        User user = resultSetToUser(rs);
        // bætum við notandanum í listann
        users.add(user);
      }
    } catch (SQLException e) {
      System.err.println("getAllUsers() failed: " + e.getMessage());
    }

    //close();
    return users;
  }

  public static void deleteUserById(int id) {
    String sql = "DELETE FROM daytrip.users WHERE id=?;";

    try (PreparedStatement pstmt = DbMain.conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      System.out.println("User " + id + " deleted");
    } catch (SQLException e) {
      System.err.println("deleteUserById() failed: " + e.getMessage());
    }
  }

}
