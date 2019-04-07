package hopurd.main.utilities;

import com.Berry.BCrypt;
import org.json.JSONArray;
import org.json.JSONObject;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.GregorianCalendar;
import java.util.Random;

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

  public static JSONObject readJSON(URL url) {
    try {
      byte[] encoded = Files.readAllBytes(Paths.get(url.getPath()));
      String content = new String(encoded, StandardCharsets.UTF_8);
      return new JSONObject(content);
    } catch (Exception e) {
      System.err.println(e.toString());
      return null;
    }
  }

  public static <T extends Enum<?>> T randomEnum(Class<T> cl) {
    SecureRandom random = new SecureRandom();
    int x = random.nextInt(cl.getEnumConstants().length);
    return cl.getEnumConstants()[x];
  }

  public static int randBetween(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must not be greater than min");
    }
    SecureRandom r = new SecureRandom();
    return r.nextInt((max - min) + 1) + min;
  }

  public static GregorianCalendar randomDate(int yearMin, int yearMax) {
    GregorianCalendar gc = new GregorianCalendar();
    int year = randBetween(yearMin, yearMax);
    gc.set(gc.YEAR, year);
    int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
    gc.set(gc.DAY_OF_YEAR, dayOfYear);
    int hour = randBetween(8, 20);
    gc.set(gc.HOUR_OF_DAY, hour);
    gc.set(gc.MINUTE, 0);
    gc.set(gc.SECOND, 0);
    gc.set(gc.MILLISECOND, 0);
    return gc;
  }
}
