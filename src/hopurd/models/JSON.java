package hopurd.models;

// enums svo lyklar séu pottþétt eins allstaðar
public class JSON {
  public enum tripJSONenum {
    NAME, CATEGORY, PRICEMIN, PRICEMAX, DURATIONMIN, DURATIONMAX,
    GROUPSIZEMIN, GROUPSIZEMAX, COUNTRY, CITY, ACCESSABILITY, LANGUAGE,
    SUSTAINABLE, DESCRIPTION, COMPANYNAME, ORDERBY
  }

  public static String resolveTrip(tripJSONenum t) {
    switch(t) {
      case NAME:
        return "name";
      case CATEGORY:
        return "category";
      case PRICEMIN:
        return "pricemin";
      case PRICEMAX:
        return "pricemax";
      case DURATIONMIN:
        return "durationMin";
      case DURATIONMAX:
        return "durationMax";
      case GROUPSIZEMIN:
        return "groupSizeMin";
      case GROUPSIZEMAX:
        return "groupSizeMax";
      case COUNTRY:
        return "country";
      case CITY:
        return "city";
      case ACCESSABILITY:
        return "accessability";
      case LANGUAGE:
        return "language";
      case SUSTAINABLE:
        return "sustainable";
      case DESCRIPTION:
        return "description";
      case COMPANYNAME:
        return "companyName";
      case ORDERBY:
        return "orderBy";
      default:
        return "";
    }
  }

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
