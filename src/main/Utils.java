package main;

public class Utils {

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

}
