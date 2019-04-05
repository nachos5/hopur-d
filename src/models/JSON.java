package models;

public class JSON {
  // enum svo review json lyklar séu pottþétt eins allstaðar
  public enum reviewJSONenum {
    USER, TITLE, TEXT, RATING, RATINGMIN, RATINGMAX, ISPUBLIC, ORDERBY
  }

  public static String resolveReview(reviewJSONenum r) {
    switch (r) {
      case USER:
        return "user";
      case TITLE:
        return "title";
      case TEXT:
        return "text";
      case RATING:
        return "rating";
      case RATINGMIN:
        return "ratingMin";
      case RATINGMAX:
        return "ratingMax";
      case ISPUBLIC:
        return "isPublic";
      case ORDERBY:
        return "orderBy";
      default:
        return "";
    }
  }
}
