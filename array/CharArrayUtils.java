package template.array;

import java.util.Random;

public class CharArrayUtils {
  private static final Random random = new Random();

  public static char min(char[] values) {
    char res = Character.MAX_VALUE;
    for (char value : values) {
      if (res > value) res = value;
    }
    return res;
  }

  public static char max(char[] values) {
    char res = Character.MIN_VALUE;
    for (char value : values) {
      if (res < value) res = value;
    }
    return res;
  }

  public static int find(char[] values, char value) {
    for (int i = 0; i < values.length; ++i) {
      if (values[i] == value) return i;
    }
    return -1;
  }

  public static void reverse(char[] values) {
    reverse(values, 0, values.length);
  }

  public static void reverse(char[] values, int fromIdx, int toIdx) {
    for (int i = fromIdx, j = toIdx - 1; i < j; ++i, --j) {
      values[i] ^= values[j];
      values[j] ^= values[i];
      values[i] ^= values[j];
    }
  }

  public static int unique(char[] values) {
    return unique(values, 0, values.length);
  }

  public static int unique(char[] values, int fromIdx, int toIdx) {
    if (fromIdx == toIdx) return 0;
    int res = 1;
    for (int i = fromIdx + 1; i < toIdx; ++i) {
      if (values[i - 1] != values[i]) {
        values[fromIdx + res++] = values[i];
      }
    }
    return res;
  }

  public static int lowerBound(char[] values, char value) {
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

  public static int upperBound(char[] values, char value) {
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

  public static void swap(char[] values, int uIdx, int vIdx) {
    if (uIdx == vIdx) return;
    values[uIdx] ^= values[vIdx];
    values[vIdx] ^= values[uIdx];
    values[uIdx] ^= values[vIdx];
  }

  /**
   * Returns {@code k}-th (0-indexed) smallest value.
   */
  public static char kth(char[] values, int kth) {
    return kthDFS(values, 0, values.length - 1, kth);
  }

  private static char kthDFS(char[] values, int lower, int upper, int kth) {
    if (lower == upper) return values[lower];
    if (lower + 1 == upper) {
      if (values[lower] > values[lower + 1]) swap(values, lower, lower + 1);
      return kth > 0 ? values[lower + 1] : values[lower];
    }
    char pivot = values[lower + random.nextInt(upper - lower)];
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
