package database.dev;

import database.models.*;
import database.BookingQueries;
import database.CompanyQueries;
import database.DepartureQueries;
import database.ReviewQueries;
import database.TripQueries;
import database.UserQueries;

import java.util.ArrayList;

/* Test til þess að prufa öll queries, endilega bæta hérna við þegar þið gerið breytingar.
   Þetta er aðallega til að testa hvort queries séu að brotna þegar breytingar eru gerðar.
   en líka til að sjá hvernig er hægt að nálgast gögn / nota föllin. Skoða console-inn. */
public class Tests {
  public static void run() {
    System.out.println("TEST BYRJA:");
    System.out.println();

    userTests();
    System.out.println();
    companyTests();
    System.out.println();
    tripTests();
    System.out.println();
    departureTests();
    System.out.println();
    reviewTests();

    System.out.println();
    System.out.println("TEST ENDA!");
    System.out.println();
  }

  private static void userTests() {
    ArrayList<User> users = UserQueries.getAllUsers();
    // allir userar og testa alla getera
    System.out.println("Allir userar:");
    for (User user: users) {
      System.out.println("Id: " + user.getId() + ", Username: " + user.getUsername() + ", Email: " + user.getEmail() +
          ", Password (hash): " + user.getPassword() + ", Admin: " + user.isAdmin());
    }

    // user eftir username-i
    User userById = UserQueries.getUser("admin");
    System.out.println("Notandi eftir username-inu admin: ");
    System.out.println("Username: " + userById.getUsername() + ", Email: " + userById.getEmail() + ", Admin: " + userById.isAdmin());
  }


  private static void companyTests() {
    ArrayList<Company> companies = CompanyQueries.getAllCompanies();
    // öll companies og testa alla getera
    System.out.println("Öll fyrirtæki:");
    for (Company company: companies) {
      System.out.println("Id: " + company.getId() + ", Name: " + company.getName() + ", Description: " +
          company.getDescription() + ", Rating: " + company.getRating());
      System.out.print("Ferðir sem þetta fyrirtæki býður uppá: ");
      for (Trip trip: company.getTrips()) {
        System.out.print(trip.getName() + ", ");
      }
      System.out.println();
    }

    // company eftir id-i
    Company companyById = CompanyQueries.getCompanyById(1);
    System.out.println("Fyrirtæki með id = 1:");
    System.out.println("Name: " + companyById.getName() + ", Description" + companyById.getDescription());

    // company eftir nafni
    Company companyByName = CompanyQueries.getCompanyByName("Epic Adventures");
    System.out.println("Fyrirtæki með nafnið Epic Adventures:");
    System.out.println("Name: " + companyByName.getName() + ", Description" + companyByName.getDescription());
  }


  private static void tripTests() {
    ArrayList<Trip> trips = TripQueries.getAllTrips();
    // öll trip og testa alla getera
    System.out.println("Öll trips:");
    for (Trip trip: trips) {
      System.out.println("Id: " + trip.getId() + ", Name: " + trip.getName() + ", Category: " + trip.getCategory() + ", Price: " +
          trip.getPrice() + ", Duration: " + trip.getDuration() + ", Group size: " + trip.getGroupSize() + ", Country: " +
          trip.getCountry() + ", City: " + trip.getCity() + ", Accessability: " + trip.getAccessability() + ", Language: " +
          trip.getLanguage() + ", Sustainable: " + trip.isSustainable() + ", Rating: " + trip.getRating() + ", Description: " +
          trip.getDescription() + ", Company: " + trip.getCompany().getName());
      System.out.println("Öll review-in fyrir þetta trip:");
      for (Review review: trip.getReviews()) {
        System.out.println("Username: " + review.getUser().getUsername() + ", Text: " + review.getText() +
            ", Rating: " + review.getRating());
      }
      System.out.println();
    }

    //  trip eftir id-i
    Trip tripById = TripQueries.getTripById(1);
    System.out.println("Trip með id = 1:");
    System.out.println("Fyrirtækið sem býður upp á ferðina: " + tripById.getCompany().getName() + ", Nafn: " + tripById.getName());

    // test eftir company id
    trips = TripQueries.getTripsByCompanyId(1);
    for (Trip trip: trips) {
      System.out.println("Fyrirtækið sem býður upp á ferðina: " + CompanyQueries.getCompanyById(1).getName() + ", Nafn: " + trip.getName());
    }
  }

  private static void departureTests() {
    ArrayList<Departure> departures = DepartureQueries.getAllDepartures();
    // Öll departure og testa alla getera
    System.out.println("Öll departures:");
    for (Departure departure: departures) {
      System.out.println("Id: " + departure.getId() + ", Trip Name: " + departure.getTrip().getName() + ", Date Begin: " +
          departure.getDateBegin().getTime() + ", Available: " + departure.getAvailable() + ", Bookings: " + departure.getBookings());
    }

    // departure eftir id-i
    Departure departureById = DepartureQueries.getDepartureById(1);
    System.out.println("Departure með id = 1:");
    System.out.println("Trip Name: " + departureById.getTrip().getName() + ", Date Begin: " + departureById.getDateBegin().getTime());
  }

  private static void reviewTests() {
    ArrayList<Review> reviews = ReviewQueries.
  }

}
