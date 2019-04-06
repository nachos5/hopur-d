package tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Klasi til að keyra test, ef virkar ekki er það örugglega af því að það vantar environments í config fyrir
 * main fallið
 */
public class TestRunner {
  public static void main(String[] args) {
    // Result result = JUnitCore.runClasses(TestJunit.class);
    Result result = JUnitCore.runClasses(TestTripUtils.class);

    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }

    System.out.println("All tests passed: " + result.wasSuccessful());
  }
}