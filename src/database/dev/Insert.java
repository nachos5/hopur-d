package database.dev;

import models.*;
import main.utilities.Utils;

import java.util.ArrayList;
import com.github.javafaker.Faker;


import static main.utilities.Utils.*;
import static database.BookingQueries.*;
import static database.CompanyQueries.*;
import static database.DepartureQueries.*;
import static database.ReviewQueries.*;
import static database.TripQueries.*;
import static database.UserQueries.*;

public class Insert {
  private static Faker faker = new Faker();
  private static int noCompanies = 20;
  private static int noTrips = 200;
  private static int noDepartures = 1000;
  private static int noUsers = 20;
  private static int noReviews = 100;
  private static int noBookings = 200;

  private static int delayBetweenInserts = 25;

  public static void run() {
    // upphafs-notendagögn
    createInitialUsers();
    // upphafs-fyrirtæki
    createInitialCompanies();
    // upphafs-ferðagögn
    createInitialTrips();
    createInitialDepartures();
    // upphafs-dómar
    createInitialReviews();
    // upphafs-bókanir
    createInitialBookings();
  }

  private static void createInitialCompanies() {
    ArrayList<Company> companies = new ArrayList<>();

    for (int i=0; i<noCompanies; i++) {
      companies.add(new Company(String.join(" ", faker.lorem().words(2)) + " Trips",
          faker.lorem().paragraph()));
    }

    for (Company company: companies) {
      insertCompany(company);
      delay(delayBetweenInserts);
    }
  }

  private static void createInitialUsers() {
    ArrayList<User> users = new ArrayList<>();
    users.add(new User("admin", true, "admin@gmail.com", Utils.hashPassword("admin")));

    for (int i=0; i<noUsers; i++) {
      users.add(new User(faker.name().username() + faker.number().numberBetween(100,999),
          false, faker.internet().emailAddress(),
          Utils.hashPassword(faker.internet().password())));
    }

    for (User user: users) {
      insertUser(user);
      delay(delayBetweenInserts);
    }
  }

  private static void createInitialTrips() {
    ArrayList<Trip> trips = new ArrayList<>();

    for (int i=0; i<noTrips; i++) {
      trips.add(new Trip(String.join(" ", faker.lorem().words(2)) + " Trip", Utils.randomEnum(Enums.Category.class), faker.number().numberBetween(30, 1000),
          faker.number().numberBetween(100, 800), faker.number().numberBetween(4, 50), Utils.randomEnum(Enums.Country.class),
          Utils.randomEnum(Enums.City.class), Utils.randomEnum(Enums.Accessability.class), Utils.randomEnum(Enums.Language.class),
          Math.random() < 0.5, faker.lorem().paragraph(), getCompanyById( faker.number().numberBetween(1, noCompanies) )));
    }

    // setjum allar ferðirnar í database-ið
    for (Trip t: trips) {
      insertTrip(t);
      delay(delayBetweenInserts); // bíðum smá á milli requesta
    }
  }

  private static void createInitialDepartures() {
    ArrayList<Departure> departures = new ArrayList<>();

    for (int i=0; i<noDepartures; i++) {
      int randTripId = faker.number().numberBetween(1, noTrips);
      Trip trip = getTripById(randTripId);
      departures.add(new Departure(trip, Utils.randomDate(2020, 2021), Math.random() < 0.5,
          faker.number().numberBetween(0, trip.getGroupSize())));
    }

    for (Departure d: departures) {
      insertDeparture(d);
      delay(delayBetweenInserts);
    }
  }

  private static void createInitialReviews() {
    ArrayList<Review> reviews = new ArrayList<>();

    for (int i=0; i<noReviews; i++) {
      User user = getUserById(faker.number().numberBetween(1, noUsers + 1));
      int randTripId = faker.number().numberBetween(1, noTrips);
      Trip trip = getTripById(randTripId);
      reviews.add(new Review(user, trip, String.join(" ", faker.lorem().words(2)), faker.lorem().sentence(),
          faker.number().randomDouble(1, 0, 5), Math.random() < 0.5));

    }

    for (Review review: reviews) {
      insertReview(review);
      delay(delayBetweenInserts);
    }
  }

  private static void createInitialBookings() {
    ArrayList<Booking> bookings = new ArrayList<>();

    for (int i=0; i<noBookings; i++) {
      User user = getUserById(faker.number().numberBetween(1, noUsers + 1));
      Departure departure = getDepartureById(faker.number().numberBetween(1, noDepartures));
      bookings.add(new Booking(user, departure, randomEnum(Enums.Status.class)));
    }

    for (Booking booking: bookings) {
      insertBooking(booking);
      delay(delayBetweenInserts);
    }
  }

}
