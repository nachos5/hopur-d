package models.utils;

import models.Review;
import models.Trip;
import models.User;

import java.util.ArrayList;

public class TripUtils {

  /**
   * Calculates the total price of the trips
   * @param trips the trips
   * @return the total price
   */
  public static int totalPrice(ArrayList<Trip> trips) {
    int total = 0;
    for (Trip trip: trips) {
      total += trip.getPrice();
    }
    return total;
  }

  /**
   * returns the duration of the trip on the format hh:mm
   * @param trip the trip
   * @return string on the format hh:mm
   */
  public static String durationToHourString(Trip trip) {
    int duration = trip.getDuration();
    int hours = duration / 60;
    int minutes = duration % 60;
    return String.format("%d:%02d", hours, minutes);
  }

  /**
   * returns the duration of the trips on the format hh:mm
   * @param trips the trips
   * @return string on the format hh:mm
   */
  public static String durationToHourString(ArrayList<Trip> trips) {
    int duration = 0;
    for (Trip trip: trips) {
      duration += trip.getDuration();
    }
    int hours = duration / 60;
    int minutes = duration % 60;
    return String.format("%d:%02d", hours, minutes);
  }

  /**
   * Calculates the mean rating of the reviews that the trip has
   * @param trip the trip
   * @return the mean
   */
  public static double reviewMean(Trip trip) {
    double mean = 0.0;
    ArrayList<Review> reviews = trip.getReviews();
    for (Review review: reviews) {
      mean += review.getRating();
    }
    mean /= reviews.size();
    return mean;
  }

  /**
   * checks if a user has already reviewed this trip
   * @param user the user to check
   * @param trip the trip to check
   * @return boolean value
   */
  public static boolean userHasReviewedTrip(User user, Trip trip) {
    ArrayList<Review> reviews = trip.getReviews();
    for (Review review: reviews) {
      if (review.getUser().getId() == user.getId()) {
        return true;
      }
    }
    return false;
  }


}
