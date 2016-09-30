package template.array;

import java.util.Random;

public class LongArrayUtils {
  private static final Random random = new Random();

  public static long min(long[] values) {
    long res = Long.MAX_VALUE;
    for (long value : values) {
      if (res > value) res = value;
    }
    return res;
  }

  public static long max(long[] values) {
    long res = Long.MIN_VALUE;
    for (long value : values) {
      if (res < value) res = value;
    }
    return res;
  }

  public static int find(long[] values, long value) {
    for (int i = 0; i < values.length; ++i) {
      if (values[i] == value) return i;
    }
    return -1;
  }

  public static void reverse(long[] values) {
    reverse(values, 0, values.length);
  }

  public static void reverse(long[] values, int fromIdx, int toIdx) {
    for (int i = fromIdx, j = toIdx - 1; i < j; ++i, --j) {
      values[i] ^= values[j];
      values[j] ^= values[i];
      values[i] ^= values[j];
    }
  }

  public static int unique(long[] values) {
    if (values.length == 0) return 0;
    int res = 1;
    for (int i = 1; i < values.length; ++i) {
      if (values[i - 1] != values[i]) {
        values[res++] = values[i];
      }
    }
    return res;
  }

  public static int lowerBound(long[] values, long value) {
    int res = values.length;
    for (int lower = 0, upper = values.length - 1; lower <= upper; ) {
      int medium = (lower + upper) >> 1;
      if (value <= values[medium]) {
        res = medium;
        upper = medium - 1;
      } else {
        lower = medium + 1;
      }
    }
    return res;
  }

  public static int upperBound(long[] values, long value) {
    int res = values.length;
    for (int lower = 0, upper = values.length - 1; lower <= upper; ) {
      int medium = (lower + upper) >> 1;
      if (value < values[medium]) {
        res = medium;
        upper = medium - 1;
      } else {
        lower = medium + 1;
      }
    }
    return res;
  }

  public static void swap(long[] values, int uIdx, int vIdx) {
    if (uIdx == vIdx) return;
    values[uIdx] ^= values[vIdx];
    values[vIdx] ^= values[uIdx];
    values[uIdx] ^= values[vIdx];
  }

  /**
   * Returns {@code k}-th (0-indexed) smallest value.
   */
  public static long kth(long[] values, int kth) {
    return kthDFS(values, 0, values.length - 1, kth);
  }

  private static long kthDFS(long[] values, int lower, int upper, int kth) {
    if (lower == upper) return values[lower];
    if (lower + 1 == upper) {
      if (values[lower] > values[lower + 1]) swap(values, lower, lower + 1);
      return kth > 0 ? values[lower + 1] : values[lower];
    }
    long pivot = values[lower + random.nextInt(upper - lower)];
    int i = lower, j = upper;
    while (i <= j) {
      for ( ; values[i] < pivot; ++i) {}
      for ( ; values[j] > pivot; --j) {}
      if (i <= j) swap(values, i++, j--);
    }
    return kth < i - lower
        ? kthDFS(values, lower, i - 1, kth)
        : kthDFS(values, i, upper, kth - i + lower);
  }
}
