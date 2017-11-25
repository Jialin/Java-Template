package template.truth;

import template.array.IntArrayUtils;

import java.util.Arrays;
import java.util.List;

public class Truth {

  public static void assertTrue(boolean o) {
    if (!o) fail("\nExpect true but was false.");
  }

  public static void assertEquals(int actual, int expect) {
    if (actual != expect) {
      fail(String.format("\nExpect %d but was %d.", expect, actual));
    }
  }

  public static void assertEquals(double actual, double expect) {
    assertEquals(actual, expect, 1E-9);
  }

  public static void assertEquals(double actual, double expect, double tolerance) {
    double diff = Math.abs(actual - expect);
    if (diff <= tolerance || (expect >= tolerance && diff / Math.abs(expect) < tolerance)) return;
    fail(String.format("\nExpect %f but was %f (diff: %f).", expect, actual, diff));
  }

  public static <T> void assertEquals(T actual, T expect) {
    if (!actual.equals(expect)) {
      fail(String.format("\nExpect %s but was %s.", expect, actual));
    }
  }

  public static void assertEquals(int[] actual, int[] expect) {
    if (actual.length != expect.length) {
      fail(String.format(
          "\nDifferent length.\nExpect %s\nActual: %s",
          IntArrayUtils.toDisplay(expect),
          IntArrayUtils.toDisplay(actual)));
    }
    for (int i = 0; i < actual.length; ++i) if (actual[i] != expect[i]) {
      fail(String.format(
          "\nDifferent at index %d.\nExpect: %s\nActual: %s",
          i,
          IntArrayUtils.toDisplay(expect),
          IntArrayUtils.toDisplay(actual)));
    }
  }

  public static <T> void assertEquals(T[] actual, T[] expect) {
    if (actual.length != expect.length) {
      fail(String.format(
          "\nDifferent length.\nExpect: %s\nActual: %s",
          Arrays.toString(expect),
          Arrays.toString(actual)));
    }
    for (int i = 0; i < actual.length; ++i) if (!actual[i].equals(expect[i])) {
      fail(String.format(
          "\nDifferent at index %d.\nExpect %s\nActual: %s",
          i,
          Arrays.toString(expect),
          Arrays.toString(actual)));
    }
  }

  public static <T> void assertEquals(List<Integer> actual, int[] expect) {
    if (actual.size() != expect.length) {
      fail(String.format(
          "\nDifferent length.\nExpect: %s\nActual: %s",
          Arrays.toString(expect),
          actual.toString()));
    }
    for (int i = 0; i < actual.size(); ++i) if (!actual.get(i).equals(expect[i])) {
      fail(String.format(
          "\nDifferent at index %d.\nExpect %s\nActual: %s",
          i,
          Arrays.toString(expect),
          actual.toString()));
    }
  }

  public static <T> void assertEquals(List<T> actual, T[] expect) {
    if (actual.size() != expect.length) {
      fail(String.format(
          "\nDifferent length.\nExpect: %s\nActual: %s",
          Arrays.toString(expect),
          actual.toString()));
    }
    for (int i = 0; i < actual.size(); ++i) if (!actual.get(i).equals(expect[i])) {
      fail(String.format(
          "\nDifferent at index %d.\nExpect %s\nActual: %s",
          i,
          Arrays.toString(expect),
          actual.toString()));
    }
  }

  public static void fail() {
    fail("");
  }

  public static void fail(String message) {
    throw new IllegalArgumentException(message);
  }
}
