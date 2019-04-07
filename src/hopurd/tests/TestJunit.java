package hopurd.tests;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestJunit {
  private String testString;

  public TestJunit() {}

  @BeforeClass
  public static void beforeAll() {
    System.out.println("Testum Junit");
  }

  @Before
  public void beforeEach() {
    testString = "Junit er að virka vel";
  }

  @After
  public void afterEach() {
    System.out.println("Test búið");
  }

  @AfterClass
  public static void afterAll() {
    System.out.println("Öll testin búin");
  }

  @Test
  public void testEins() {
    String str = "Junit er að virka vel";
    assertEquals(str, testString);
    testString += "!";
    assertEquals(str + "!", testString);
  }

  @Test
  public void testOlikt() {
    String str = "Allt annar strengur";
    assertNotEquals(str, testString);
  }
}