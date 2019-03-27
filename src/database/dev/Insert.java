package database.dev;

import models.*;
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
    ArrayList<CompanyModel> companies = new ArrayList<>();
    companies.add(new CompanyModel("Flott fyrirtæki", 4.5, "Flottar ferðir hjá flottu fyrirtæki"));
    companies.add(new CompanyModel("Epic Adventures", 5.0, "Enjoy some epic adventures, perfect for lit people!"));

    for (CompanyModel company: companies) {
      insertCompany(company);
      delay(50);
    }
  }

  private static void createInitialUsers() {
    ArrayList<UserModel> users = new ArrayList<>();
    users.add(new UserModel("admin", true, "admin@gmail.com", Utils.hashPassword("admin")));
    users.add(new UserModel("user", false,"user@gmail.com", Utils.hashPassword("siggi")));
    users.add(new UserModel("Bubbi", false, "bubbi@gmail.com", Utils.hashPassword("bubbi")));

    for (UserModel user: users) {
      insertUser(user);
      delay(50);
    }
  }

  private static void createInitialTrips() {
    ArrayList<TripModel> trips = new ArrayList<>();
    trips.add(new TripModel("Fjallganga", Enums.Category.FJALLGANGA, 15000, 420, 5, Enums.Country.IS, Enums.City.RVK,
        Enums.Accessability.MEDIUM, Enums.Language.IS, true, 4.0, "Fjallganga fyrir frískt fólk!", getCompanyById(1)));
    trips.add(new TripModel("Glacier trip", Enums.Category.JOKLAFERD, 20000, 123, 10, Enums.Country.US, Enums.City.NY,
        Enums.Accessability.BRUTAL, Enums.Language.EN, true, 4.5, "Epic glacier trip in New York, the home of glaciers!", getCompanyById(2)));
    trips.add(new TripModel("Bus trip", Enums.Category.RUTUFERD, 10000, 333, 24, Enums.Country.UK, Enums.City.LON,
        Enums.Accessability.EASY, Enums.Language.EN, false, 3.5, "Savage bus trip m8!", getCompanyById(2)));

    // setjum allar ferðirnar í database-ið
    for (TripModel t: trips) {
      insertTrip(t);
      delay(50); // bíðum smá á milli requesta
    }
  }

  private static void createInitialDepartures() {
    ArrayList<DepartureModel> departures = new ArrayList<>();
    departures.add(new DepartureModel(getTripById(1), new GregorianCalendar(2019, Calendar.JUNE, 10, 12, 30, 0), true, 2));
    departures.add(new DepartureModel(getTripById(2), new GregorianCalendar(2019, Calendar.JULY, 20, 8, 0, 0), false, 10));
    departures.add(new DepartureModel(getTripById(3), new GregorianCalendar(2019, Calendar.AUGUST, 30, 11, 20, 0), true, 0));

    for (DepartureModel d: departures) {
      insertDeparture(d);
      delay(50);
    }
  }

  private static void createInitialReviews() {
    ArrayList<ReviewModel> reviews = new ArrayList<>();
    reviews.add(new ReviewModel(getUser("admin"), getTripById(1), "Vá!", "Frábær ganga!", 5.0, true));
    reviews.add(new ReviewModel(getUser("user"), getTripById(1), "Glatað!", "Ömurleg ganga!", 0.5, true));
    reviews.add(new ReviewModel(getUser("Bubbi"), getTripById(2), "Farið til helvítis!", "Ætla að kæra ykkur fyrir ósættanlega framkomu!", 0.0, false));

    for (ReviewModel review: reviews) {
      insertReview(review);
      delay(50);
    }
  }

  private static void createInitialBookings() {
    ArrayList<BookingModel> bookings = new ArrayList<>();
    bookings.add(new BookingModel(getUser("admin"), getDepartureById(1), Enums.Status.UNPAID));
    bookings.add(new BookingModel(getUser("admin"), getDepartureById(2), Enums.Status.PAID));
    bookings.add(new BookingModel(getUser("Bubbi"), getDepartureById(3), Enums.Status.UNPAID));

    for (BookingModel booking: bookings) {
      insertBooking(booking);
      delay(50);
    }
  }

}
