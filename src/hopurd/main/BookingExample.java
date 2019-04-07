package hopurd.main;

import hopurd.database.BookingQueries;
import hopurd.database.DepartureQueries;
import hopurd.database.TripQueries;
import hopurd.database.UserQueries;
import hopurd.models.*;

public class BookingExample {

  public static void BookingExampleStatic() {

    // ferðin sem við erum með
    Trip trip = TripQueries.getTripByName("optio a trip");
    System.out.println(trip.getName());
    // sækjum departures fyrir ferðina (þið þurfið ekkert endilega að pæla í departures kannski óþarflega
    // flókið, ef ekki notið þá bara fyrsta eða eitthvað
    Departure departure = DepartureQueries.getAllTripDepartures(trip.getId()).get(0); // sækjum bara fyrsta
    // user úr okkar usertöflu eftir username-i
    User user = UserQueries.getUser("admin");
    // búa til bókun fyrir þann user með þessu departurei á ferðinni
    Booking booking = new Booking(user, departure, Enums.Status.PAID);
    // búum til bókunina í databaseinu
    BookingQueries.insertBooking(booking);

  }

}
