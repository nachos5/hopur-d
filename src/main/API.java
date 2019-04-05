package main;

import models.*;
import models.JSON.*;
import static models.JSON.*;
import static database.ReviewQueries.*;

import org.json.JSONObject;
import java.util.ArrayList;

public class API {

  /**
   * gets reviews by rating range
   * @param min double value that is equal to or larger than 0.0
   * @param max double value that is equal to or less than 5.0
   * @param asc boolean for ascending or descending ordering
   * @return ArrayList containing the reviews
   */
  public static ArrayList<Review> getReviewsByRatingRange(double min, double max, boolean asc) {
    if (min < 0.0 || max > 5.0)
      throw new IllegalArgumentException("min has to be equal to or larger than 0.0 and max equal to or less than 5.0");

    JSONObject reviewJSON = new JSONObject();
    reviewJSON.put(resolveReview(reviewJSONenum.RATINGMIN), min);
    reviewJSON.put(resolveReview(reviewJSONenum.RATINGMAX), max);
    String ascOrDesc = "asc";
    if (!asc) ascOrDesc = "desc";
    reviewJSON.put(resolveReview(reviewJSONenum.ORDERBY), "rating " + ascOrDesc);

    ArrayList<Review> reviews = dynamicReviewQuery(reviewJSON);
    return reviews;
  }

  /**
   * @return ArrayList containing the public reviews
   */
  public static ArrayList<Review> getPublicReviews() {
    JSONObject reviewJSON = new JSONObject();
    reviewJSON.put(resolveReview(reviewJSONenum.ISPUBLIC), "true");
    reviewJSON.put(resolveReview(reviewJSONenum.ORDERBY), "created desc");

    ArrayList<Review> reviews = dynamicReviewQuery(reviewJSON);
    return reviews;
  }

}
