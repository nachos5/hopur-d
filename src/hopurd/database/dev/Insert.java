package hopurd.database.dev;

import hopurd.database.*;
import hopurd.models.*;
import hopurd.models.*;
import hopurd.main.utilities.Utils;

import java.util.ArrayList;
import com.github.javafaker.Faker;

import static hopurd.main.utilities.Utils.delay;
import static hopurd.main.utilities.Utils.randomEnum;

public class Insert {
  private static Faker faker = new Faker();
  private static int noCompanies = 10;
  private static int noTrips = 20;
  private static int noDepartures = 40;
  private static int noUsers = 5;
  private static int noReviews = 10;
  private static int noBookings = 20;

  private static int delayBetweenInserts = 50;

  public static void run() {
//    // upphafs-notendagögn
//    createInitialUsers();
//    // upphafs-fyrirtæki
//    createInitialCompanies();
//    // upphafs-ferðagögn
//    createInitialTrips();
    createInitialDepartures();
//    // upphafs-dómar
//    createInitialReviews();
//    // upphafs-bókanir
//    createInitialBookings();
  }

  private static void createInitialCompanies() {
    ArrayList<Company> companies = new ArrayList<>();

    for (int i=0; i<noCompanies; i++) {
      companies.add(new Company(String.join(" ", faker.lorem().words(2)) + " trips",
          faker.lorem().paragraph()));
    }

    for (Company company: companies) {
      CompanyQueries.insertCompany(company);
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
    users.add(new User("user", false,"user@gmail.com", Utils.hashPassword("user")));
    users.add(new User("testuser2", false, "user2@gmail.com", Utils.hashPassword("bubbi")));

    for (User user: users) {
      UserQueries.insertUser(user);
      delay(delayBetweenInserts);
    }
  }

  private static void createInitialTrips() {
    ArrayList<Trip> trips = new ArrayList<>();

    for (int i=0; i<noTrips; i++) {
      trips.add(new Trip(String.join(" ", faker.lorem().words(2)) + " trip", randomEnum(Enums.Category.class), faker.number().numberBetween(30, 1000),
          faker.number().numberBetween(100, 800), faker.number().numberBetween(4, 50), randomEnum(Enums.Country.class),
          randomEnum(Enums.City.class), randomEnum(Enums.Accessability.class), randomEnum(Enums.Language.class),
          Math.random() < 0.5, faker.lorem().paragraph(), CompanyQueries.getCompanyById( faker.number().numberBetween(1, noCompanies) )));
    }

    // setjum allar ferðirnar í database-ið
    for (Trip t: trips) {
      TripQueries.insertTrip(t);
      delay(delayBetweenInserts); // bíðum smá á milli requesta
    }
  }

  private static void createInitialDepartures() {
    ArrayList<Departure> departures = new ArrayList<>();

    for (int i=0; i<noDepartures; i++) {
      int randTripId = faker.number().numberBetween(1, noTrips);
      Trip trip = null;
      if (i < noTrips)
        trip = TripQueries.getTripById(i+1);
      else
        trip = TripQueries.getTripById(randTripId);
      departures.add(new Departure(trip, Utils.randomDate(2020, 2021), Math.random() < 0.5,
          //faker.number().numberBetween(0, trip.getGroupSize())));
          0));
    }

    for (Departure d: departures) {
      DepartureQueries.insertDeparture(d);
      delay(delayBetweenInserts);
    }
  }

  private static void createInitialReviews() {
    ArrayList<Review> reviews = new ArrayList<>();

    for (int i=0; i<noReviews; i++) {
      User user = UserQueries.getUserById(faker.number().numberBetween(1, noUsers + 1));
      int randTripId = faker.number().numberBetween(1, noTrips);
      Trip trip = TripQueries.getTripById(randTripId);
      reviews.add(new Review(user, trip, String.join(" ", faker.lorem().words(2)), faker.lorem().sentence(),
          faker.number().randomDouble(1, 0, 5), Math.random() < 0.5));

    }
    reviews.add(new Review(UserQueries.getUser("admin"), TripQueries.getTripById(1), "Vá!", "Frábær ganga!", 5.0, true));
    reviews.add(new Review(UserQueries.getUser("user"), TripQueries.getTripById(1), "Glatað!", "Ömurleg ganga!", 0.5, true));
    reviews.add(new Review(UserQueries.getUser("testuser2"), TripQueries.getTripById(2), "Farið til helvítis!", "Ætla að kæra ykkur fyrir ósættanlega framkomu!", 0.0, false));

    for (Review review: reviews) {
      ReviewQueries.insertReview(review);
      delay(delayBetweenInserts);
    }
  }

  private static void createInitialBookings() {
    ArrayList<Booking> bookings = new ArrayList<>();

    for (int i=0; i<noBookings; i++) {
      User user = UserQueries.getUserById(faker.number().numberBetween(1, noUsers + 1));
      Departure departure = DepartureQueries.getDepartureById(faker.number().numberBetween(1, noDepartures));
      bookings.add(new Booking(user, departure, randomEnum(Enums.Status.class)));
    }
    bookings.add(new Booking(UserQueries.getUser("admin"), DepartureQueries.getDepartureById(1), Enums.Status.UNPAID));
    bookings.add(new Booking(UserQueries.getUser("admin"), DepartureQueries.getDepartureById(2), Enums.Status.PAID));
    bookings.add(new Booking(UserQueries.getUser("user"), DepartureQueries.getDepartureById(1), Enums.Status.UNPAID));

    for (Booking booking: bookings) {
      BookingQueries.insertBooking(booking);
      delay(delayBetweenInserts);
    }
  }

}
