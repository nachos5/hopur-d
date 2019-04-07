//package database.dev;
//
//import BookingQueries;
//import CompanyQueries;
//import DepartureQueries;
//import ReviewQueries;
//import TripQueries;
//import UserQueries;
//import models.*;
//
//import java.util.ArrayList;
//
//
//// BAIL Á ÞESSU, ÓÞARFI AÐ PÆLA Í ÞESSU
//
//public class DbTests {
//  public static void run() {
//    System.out.println("TEST BYRJA:");
//    System.out.println();
//
//    userTests();
//    System.out.println();
//    companyTests();
//    System.out.println();
//    tripTests();
//    System.out.println();
//    departureTests();
//    System.out.println();
//    reviewTests();
//    System.out.println();
//    bookingTests();
//    System.out.println();
//    deleteTests();
//
//    System.out.println();
//    System.out.println("TEST ENDA!");
//    System.out.println();
//  }
//
//  private static void userTests() {
//    ArrayList<User> users = UserQueries.getAllUsers();
//    // allir userar og testa alla getera
//    System.out.println("Allir userar:");
//    for (User user: users) {
//      System.out.println("Id: " + user.getId() + ", Username: " + user.getUsername() + ", Email: " + user.getEmail() +
//          ", Password (hash): " + user.getPassword() + ", Admin: " + user.isAdmin());
//    }
//
//    // user eftir username-i
//    User userById = UserQueries.getUser("admin");
//    System.out.println("Notandi eftir username-inu admin: ");
//    System.out.println("Username: " + userById.getUsername() + ", Email: " + userById.getEmail() + ", Admin: " + userById.isAdmin());
//  }
//
//
//  private static void companyTests() {
//    ArrayList<Company> companies = CompanyQueries.getAllCompanies();
//    // öll companies og testa alla getera
//    System.out.println("Öll fyrirtæki:");
//    for (Company company: companies) {
//      System.out.println("Id: " + company.getId() + ", Name: " + company.getName() + ", Description: " +
//          company.getDescription() + ", Rating: " + company.getRating());
//      System.out.print("Ferðir sem þetta fyrirtæki býður uppá: ");
//      for (Trip trip: company.getTrips()) {
//        System.out.print(trip.getName() + ", ");
//      }
//      System.out.println();
//    }
//
//    // company eftir id-i
//    Company companyById = CompanyQueries.getCompanyById(1);
//    System.out.println("Fyrirtæki með id = 1:");
//    System.out.println("Name: " + companyById.getName() + ", Description" + companyById.getDescription());
//
//    // company eftir nafni
//    Company companyByName = CompanyQueries.getCompanyByName("Epic Adventures");
//    System.out.println("Fyrirtæki með nafnið Epic Adventures:");
//    System.out.println("Name: " + companyByName.getName() + ", Description" + companyByName.getDescription());
//  }
//
//
//  private static void tripTests() {
//    ArrayList<Trip> trips = TripQueries.getAllTrips();
//    // öll trip og testa alla getera
//    System.out.println("Öll trips:");
//    for (Trip trip: trips) {
//      System.out.println("Id: " + trip.getId() + ", Name: " + trip.getName() + ", Category: " + trip.getCategory() + ", Price: " +
//          trip.getPrice() + ", Duration: " + trip.getDuration() + ", Group size: " + trip.getGroupSize() + ", Country: " +
//          trip.getCountry() + ", City: " + trip.getCity() + ", Accessability: " + trip.getAccessability() + ", Language: " +
//          trip.getLanguage() + ", Sustainable: " + trip.isSustainable() + ", Rating: " + trip.getRating() + ", Description: " +
//          trip.getDescription() + ", Company: " + trip.getCompany().getName());
//      System.out.println("Öll review-in fyrir þetta trip:");
//      for (Review review: trip.getReviews()) {
//        System.out.println("Username: " + review.getUser().getUsername() + ", Text: " + review.getText() +
//            ", Rating: " + review.getRating());
//      }
//      System.out.println();
//    }
//
//    //  trip eftir id-i
//    Trip tripById = TripQueries.getTripById(1);
//    System.out.println("Trips með id = 1:");
//    System.out.println("Fyrirtækið sem býður upp á ferðina: " + tripById.getCompany().getName() + ", Nafn: " + tripById.getName());
//
//    // test eftir company id
//    trips = TripQueries.getTripsByCompanyId(1);
//    for (Trip trip: trips) {
//      System.out.println("Fyrirtækið sem býður upp á ferðina: " + CompanyQueries.getCompanyById(1).getName() + ", Nafn: " + trip.getName());
//    }
//  }
//
//  private static void departureTests() {
//    ArrayList<Departure> departures = DepartureQueries.getAllDepartures();
//    // Öll departure og testa alla getera
//    System.out.println("Öll departures:");
//    for (Departure departure: departures) {
//      System.out.println("Id: " + departure.getId() + ", Trips Name: " + departure.getTrip().getName() + ", Date Begin: " +
//          departure.getDateBegin().getTime() + ", Available: " + departure.getAvailable() + ", Bookings: " + departure.getBookings());
//    }
//
//    // departure eftir id-i
//    Departure departureById = DepartureQueries.getDepartureById(1);
//    System.out.println("Departures með id = 1:");
//    System.out.println("Trips Name: " + departureById.getTrip().getName() + ", Date Begin: " + departureById.getDateBegin().getTime());
//  }
//
//  private static void reviewTests() {
//    ArrayList<Review> reviews = ReviewQueries.getAllReviews();
//    // öll review og testa alla getera
//    System.out.println("Öll reviews:");
//    for (Review review: reviews) {
//      System.out.println("Id: " + review.getId() + ", Trips Name: " + review.getTrip().getName() + ", Username: " +
//          review.getUser().getUsername() + ", Title: " + review.getTitle() + ", Text: " + review.getText() + ", Rating: " +
//          review.getRating() + ", Is Public: " + review.getIsPublic());
//    }
//
//    // review eftir id-i
//    Review reviewById = ReviewQueries.getReviewById(1);
//    System.out.println("Reviews með id = 1:");
//    System.out.println("Username: " + reviewById.getUser().getUsername() + ", Text: " + reviewById.getText());
//  }
//
//  private static void bookingTests() {
//    ArrayList<Booking> bookings = BookingQueries.getAllBookings();
//    // öll bookings og testa alla getera
//    System.out.println("Öll bookings:");
//    for (Booking booking: bookings) {
//      System.out.println("Id: " + booking.getId() + ", Username: " + booking.getUser().getUsername() + ", Trips Name: " +
//          booking.getDeparture().getTrip().getName() + ", Departs: " + booking.getDeparture().getDateBegin().getTime() +
//          ", Booked at: " + booking.getBookedAt().getTime() + ", Status: " + booking.getStatus());
//    }
//
//    // booking eftir id-i
//    Booking booking = BookingQueries.getBookingById(1);
//    System.out.println("Bookings með id = 1:");
//    System.out.println("Username: " + booking.getUser().getUsername() + ", Trips Name: " + booking.getDeparture().getTrip().getName() +
//        ", Departs: " + booking.getDeparture().getDateBegin().getTime());
//  }
//
//  private static void deleteTests() {
//    ReviewQueries.deleteReviewById(1);
//    ReviewQueries.deleteReviewById(2);
//    BookingQueries.deleteBookingById(1);
//    BookingQueries.deleteBookingById(2);
//    UserQueries.deleteUserById(1);
//    DepartureQueries.deleteDepartureById(1);
//    TripQueries.deleteTripById(1);
//    CompanyQueries.deleteCompanyById(1);
//  }
//
//}
