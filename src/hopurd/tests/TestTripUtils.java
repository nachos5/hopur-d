package hopurd.tests;

import hopurd.database.CompanyQueries;
import hopurd.database.DbMain;
import hopurd.database.TripQueries;
import hopurd.database.UserQueries;
import hopurd.models.Enums;
import hopurd.models.Review;
import hopurd.models.Trip;
import hopurd.models.User;
import hopurd.models.utils.TripUtils;
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

  /**
   * duration is stored as an integer of minutes, lets test when converting to a hh:mm string
   */
  @Test
  public void testDuration() {
    String durationFromUtils = TripUtils.durationToHourString(trip);
    int duration = trip.getDuration();
    int hours = duration / 60;
    int minutes = duration % 60;
    String durationString =  String.format("%d:%02d", hours, minutes);

    assertEquals(durationFromUtils, durationString);
  }

  /**
   * the mean of all the review ratings of the trip
   */
  @Test
  public void testReviewsMean() {
    double mean = TripUtils.reviewMean(trip);
    // the range should be 0.0 - 5.0
    assertTrue(mean >= 0.0 && mean <= 5.0);
  }

  /**
   * there is one review per user, lets check if that util function functions like it's supposed to
   */
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

  /**
   * lets test getting the total all-time booking count of the trip
   */
  @Test
  public void testTotalBookings() {
    int total = TripUtils.getTotalBookings(trips.get(0));
    assertTrue(total >= 0);
  }

}
