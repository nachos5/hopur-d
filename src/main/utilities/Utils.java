package main.utilities;

import com.Berry.BCrypt;
import database.DbMain;
import database.models.User;

public final class Utils {

  private static User currentUser;

  /**
   * fall til að stöðva keyrslu í x ms
   * @param x ms
   */
  public static void delay(int x) {
    try {
      Thread.sleep(x);
    }
    catch(InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  public static String hashPassword(String password) {
    String salt = BCrypt.gensalt();
    return BCrypt.hashpw(password, salt);
  }

  public static Boolean checkPassword(String password, String hash) {
    return BCrypt.checkpw(password, hash);
  }

  public static void login(String user, String password) {
    User u = DbMain.getUser(user);
    String p = u.getPassword();

    if (checkPassword(password, p)) currentUser = u;
    else currentUser = null;
  }

  public static void logout() {
    currentUser = null;
  }

  public static User getCurrentUser() {
    return currentUser;
  }
}
