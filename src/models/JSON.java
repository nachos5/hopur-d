package models;

// enums svo lyklar séu pottþétt eins allstaðar
public class JSON {
  public enum tripJSONenum {
    NAME, CATEGORY, MAXPRICE, MINPRICE, MAXDURATION, MINDURATION, GROUPSIZE, COUNTRY, CITY, ACCESSABILITY, LANGUAGE,
    SUSTAINABLE, DESCRIPTION, COMPANYID, STARTDATE, ENDDATE
  }

  public static String resolveTrip(tripJSONenum t) {
    switch(t) {
      case NAME:
        return "name";
      case CATEGORY:
        return "category";
      case MAXPRICE:
        return "maxPrice";
      case MINPRICE:
        return "minPrice";
      case MAXDURATION:
        return "maxDuration";
      case MINDURATION:
        return "minDuration";
      case GROUPSIZE:
        return "groupSize";
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
      case COMPANYID:
        return "companyId";
      case STARTDATE:
        return "startDate";
      case ENDDATE:
        return "endDate";
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
