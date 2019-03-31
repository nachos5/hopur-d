package tests;

import database.CompanyQueries;
import database.DbMain;
import database.TripQueries;
import database.UserQueries;
import models.Enums;
import models.Review;
import models.Trip;
import models.User;
import models.utils.TripUtils;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestTripUtils {

  private static ArrayList<Trip> trips;
  private static Trip trip;

  public TestTripUtils() {}

  /**
   * Lets get all the trips from the database before executing
   */
  @BeforeClass
  public static void beforeAll() {
    System.out.println("Testing trips...");
    DbMain.init(false);
    trips = TripQueries.getAllTrips();
  }

  /**
   * Lets create a new trip before each test
   */
  @Before
  public void before() {
    ArrayList<Review> reviews = new ArrayList<>();
    reviews.add(new Review(UserQueries.getUser("testuser1"), "This trip is so lit!", "Totally lit trip! Would recommend for " +
        "everyone!", 5.0, true));
    reviews.add(new Review(UserQueries.getUser("testuser2"), "This trip sucks!", "Such a lame trip, dirty bus windows and loud " +
        "asians!", 0.5, true));
    trip = new Trip("Epic trip!", Enums.Category.BUS, 99, 180, 10, Enums.Country.IS, Enums.City.RVK, Enums.Accessability.EASY,
        Enums.Language.EN, false, "This trip is so epic! You have to do this before you die!!!!", CompanyQueries.getCompanyById(1), reviews);
  }

  @After
  public void after() {
    trip = null;
  }

  @AfterClass
  public static void afterAll() {
    System.out.println("Done testing trips");
  }

  @Test
  public void testTotalPrice() {
    int totalFromUtils = TripUtils.totalPrice(trips);
    int total = 0;
    for (Trip trip: trips) {
      total += trip.getPrice();
    }

    assertTrue(totalFromUtils >= 0);
    assertEquals(totalFromUtils, total);
    assertNotEquals(totalFromUtils, "þetta er strengur");
  }

  @Test
  public void testDuration() {
    String durationFromUtils = TripUtils.durationToHourString(trip);
    int duration = trip.getDuration();
    int hours = duration / 60;
    int minutes = duration % 60;
    String durationString =  String.format("%d:%02d", hours, minutes);

    assertEquals(durationFromUtils, durationString);
  }

  @Test
  public void testReviewsMean() {
    double mean = TripUtils.reviewMean(trip);
    // meðaltalið ætti ekki að fara yfir 5.0 eða undir 0.0
    assertTrue(mean <= 5.0 && mean >= 0.0);
  }

  @Test
  public void testIfUserHasReviewedTrip() {
    User user = UserQueries.getUser("admin");
    boolean hasReviewed = TripUtils.userHasReviewedTrip(user, trip);

    assertFalse(hasReviewed);

    Review review = new Review(user, "Very nice", "Very nice trip", 4.0, true);
    trip.addReview(review);
    hasReviewed = TripUtils.userHasReviewedTrip(user, trip);

    assertTrue(hasReviewed);
  }

  @Test
  public void testTotalBookings() {
    int total = TripUtils.getTotalBookings(trips.get(0));
    assertTrue(total >= 0);
  }

}
