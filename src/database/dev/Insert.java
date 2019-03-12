package database.dev;

import database.models.Trip;
import database.models.User;
import database.models.Review;
import main.utilities.Utils;

import static database.DbMain.*;
import static main.utilities.Utils.*;

public class Insert {
  public static void run() {
    // upphafs-notendagögn
    createInitialUsers();
    // upphafs-ferðagögn
    createInitialTrips();
    // upphafs-dómar
    createInitialReviews();
  }

  private static void createInitialUsers() {
    User user1 = new User("admin", true, "admin@gmail.com", Utils.hashPassword("admin"));
    User user2 = new User("user", false,"user@gmail.com", Utils.hashPassword("siggi"));
    User user3 = new User("Bubbi", false, "bubbi@gmail.com", Utils.hashPassword("bubbi"));

    User[] users = {
        user1, user2, user3
    };

    for (User user: users) {
      delay(100);
      insertUser(user);
      delay(100);
    }
  }

  private static void createInitialTrips() {
    Trip trip1 = new Trip("Fjallganga", 100);
    Trip trip2 = new Trip("Jöklaferð", 200);
    Trip trip3 = new Trip("Rútuferð", 300);

    Trip[] trips = {
        trip1, trip2, trip3
    };

    // setjum allar ferðirnar í database-ið
    for (Trip t: trips) {
      delay(100); // bíðum smá á milli requesta
      insertTrip(t);
      delay(100);
    }
  }

  private static void createInitialReviews() {
    Review review1 = new Review(1, 1, "Frábær ganga!");
    Review review2 = new Review(2, 1, "Ömurleg ganga!");
    Review review3 = new Review(3, 2, "Ætla að kæra ykkur fyrir ósættanlega framkomu!");

    Review[] reviews = {
        review1, review2, review3
    };

    for (Review review : reviews) {
      delay(100);
      insertReview(review);
      delay(100);
    }
  }
}
