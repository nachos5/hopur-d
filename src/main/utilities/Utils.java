package main.utilities;

import com.Berry.BCrypt;

public final class Utils {

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
}
