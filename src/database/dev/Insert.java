package database.dev;

import database.models.Trip;
import database.models.User;
import database.models.Review;

import static database.DbMain.*;
import static main.Utils.delay;

public class Insert {
  public static void run() {
    // upphafs-notendagögn
    createInitialUsers();
    // upphafs-ferðagögn
    createInitialTrips();
    // upphafs-dómar
    createInitialReviews();
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

  private static void createInitialUsers() {
    User user1 = new User("a", "a@a.is");
    User user2 = new User("b", "b@b.is");
    User user3 = new User("c", "c@c.is");

    User[] users = {
        user1, user2, user3
    };

    for (User user: users) {
      delay(100);
      insertUser(user);
      delay(100);
    }
  }

  private static void createInitialReviews() {
    Review review1 = new Review("a@a.is", 1, "Frábær ganga!");
    Review review2 = new Review("b@b.is", 1, "Ömurleg ganga!");
    Review review3 = new Review("c@c.is", 2, "Ætla að kæra ykkur fyrir ósættanlega framkomu!");

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
