package database.dev;

import database.models.*;
import main.utilities.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static main.utilities.Utils.*;
import static database.BookingQueries.*;
import static database.CompanyQueries.*;
import static database.DepartureQueries.*;
import static database.ReviewQueries.*;
import static database.TripQueries.*;
import static database.UserQueries.*;

public class Insert {
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
    companies.add(new Company("Flott fyrirtæki", 4.5, "Flottar ferðir hjá flottu fyrirtæki"));
    companies.add(new Company("Epic Adventures", 5.0, "Enjoy some epic adventures, perfect for lit people!"));

    for (Company company: companies) {
      insertCompany(company);
      delay(50);
    }
  }

  private static void createInitialUsers() {
    ArrayList<User> users = new ArrayList<>();
    users.add(new User("admin", true, "admin@gmail.com", Utils.hashPassword("admin")));
    users.add(new User("user", false,"user@gmail.com", Utils.hashPassword("siggi")));
    users.add(new User("Bubbi", false, "bubbi@gmail.com", Utils.hashPassword("bubbi")));

    for (User user: users) {
      insertUser(user);
      delay(50);
    }
  }

  private static void createInitialTrips() {
    ArrayList<Trip> trips = new ArrayList<>();
    trips.add(new Trip("Fjallganga", Enums.Category.FJALLGANGA, 15000, 420, 5, Enums.Country.IS, Enums.City.RVK,
        Enums.Accessability.MEDIUM, Enums.Language.IS, true, 4.0, "Fjallganga fyrir frískt fólk!", getCompanyById(1)));
    trips.add(new Trip("Glacier trip", Enums.Category.JOKLAFERD, 20000, 123, 10, Enums.Country.US, Enums.City.NY,
        Enums.Accessability.BRUTAL, Enums.Language.EN, true, 4.5, "Epic glacier trip in New York, the home of glaciers!", getCompanyById(2)));
    trips.add(new Trip("Bus trip", Enums.Category.RUTUFERD, 10000, 333, 24, Enums.Country.UK, Enums.City.LON,
        Enums.Accessability.EASY, Enums.Language.EN, false, 3.5, "Savage bus trip m8!", getCompanyById(2)));

    // setjum allar ferðirnar í database-ið
    for (Trip t: trips) {
      insertTrip(t);
      delay(50); // bíðum smá á milli requesta
    }
  }

  private static void createInitialDepartures() {
    ArrayList<Departure> departures = new ArrayList<>();
    departures.add(new Departure(getTripById(1), new GregorianCalendar(2019, Calendar.JUNE, 10, 12, 30, 0), true, 2));
    departures.add(new Departure(getTripById(2), new GregorianCalendar(2019, Calendar.JULY, 20, 8, 0, 0), false, 10));
    departures.add(new Departure(getTripById(3), new GregorianCalendar(2019, Calendar.AUGUST, 30, 11, 20, 0), true, 0));

    for (Departure d: departures) {
      insertDeparture(d);
      delay(50);
    }
  }

  private static void createInitialReviews() {
    ArrayList<Review> reviews = new ArrayList<>();
    reviews.add(new Review(getUser("admin"), getTripById(1), "Vá!", "Frábær ganga!", 5.0, true));
    reviews.add(new Review(getUser("user"), getTripById(1), "Glatað!", "Ömurleg ganga!", 0.5, true));
    reviews.add(new Review(getUser("Bubbi"), getTripById(2), "Farið til helvítis!", "Ætla að kæra ykkur fyrir ósættanlega framkomu!", 0.0, false));

    for (Review review: reviews) {
      insertReview(review);
      delay(50);
    }
  }

  private static void createInitialBookings() {
    ArrayList<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking(getUser("admin"), getDepartureById(1), Enums.Status.UNPAID));
    bookings.add(new Booking(getUser("admin"), getDepartureById(2), Enums.Status.PAID));
    bookings.add(new Booking(getUser("Bubbi"), getDepartureById(3), Enums.Status.UNPAID));

    for (Booking booking: bookings) {
      insertBooking(booking);
      delay(50);
    }
  }

}
