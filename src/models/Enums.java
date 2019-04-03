package models;

import java.util.ArrayList;

/**
 * Enums til að staðfesta að ákveðin gögn sem fara í database-ið séu rétt
 */
public class Enums {

  public enum Category {
    MOUNTAIN, GLACIER, BUS
  }

  public static String resolveCategory(Category c) {
    switch (c) {
      case MOUNTAIN:
        return "Mountain trip";
      case GLACIER:
        return "Glacier trip";
      case BUS:
        return "Bus trip";
      default: // kemst augljóslega aldrei hingað en java vælir ef þetta er ekki...
        return "";
    }
  }

  public enum Country {
    IS, UK, US
  }

  public static String resolveCountry(Country c) {
    switch (c) {
      case IS:
        return "Ísland";
      case UK:
        return "United Kingdom";
      case US:
        return "United States of America";
      default:
        return "";
    }
  }

  public enum City {
    RVK, LON, NY
  }

  public static String resolveCity(City c) {
    switch (c) {
      case RVK:
        return "Reykjavík";
      case LON:
        return "London";
      case NY:
        return "New York";
      default:
        return "";
    }
  }

  public enum Accessability {
    EASY, MEDIUM, HARD, BRUTAL
  }

  public static String resolveAccessability(Accessability a) {
    switch (a) {
      case EASY:
        return "Auðveld";
      case MEDIUM:
        return "Miðlungs";
      case HARD:
        return "Erfið";
      case BRUTAL:
        return "Mjög erfið";
      default:
        return "";
    }
  }

  public enum Language {
    IS, EN
  }

  public static String resolveLanguage(Language l) {
    switch (l) {
      case EN:
        return "English";
      case IS:
        return "Íslenska";
      default:
        return "";
    }
  }

  public enum Status {
    UNPAID, PAID
  }

  public static String resolveStatus(Status s) {
    switch (s) {
      case PAID:
        return "Greitt";
      case UNPAID:
        return "Ógreitt";
      default:
        return "";
    }
  }

}
