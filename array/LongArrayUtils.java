package template.array;

import java.util.Arrays;
import java.util.Random;

public class LongArrayUtils {
  private static final Random RANDOM = new Random(1000000007);

  public static long min(long[] values) {
    return min(values, 0, values.length);
  }

  public static long min(long[] values, int fromIdx, int toIdx) {
    long res = Long.MAX_VALUE;
    for (int i = fromIdx; i < toIdx; ++i) {
      if (res > values[i]) res = values[i];
    }
    return res;
  }

  public static long max(long[] values) {
    return max(values, 0, values.length);
  }

  public static long max(long[] values, int fromIdx, int toIdx) {
    long res = Long.MIN_VALUE;
    for (int i = fromIdx; i < toIdx; ++i) {
      if (res < values[i]) res = values[i];
    }
    return res;
  }

  public static int find(long[] values, long value) {
    return find(values, 0, values.length, value);
  }

  public static int find(long[] values, int fromIdx, int toIdx, long value) {
    for (int i = fromIdx; i < toIdx; ++i) {
      if (values[i] == value) return i;
    }
    return -1;
  }

  public static int findLast(long[] values, long value) {
    return find(values, 0, values.length, value);
  }

  public static int findLast(long[] values, int fromIdx, int toIdx, long value) {
    for (int i = toIdx - 1; i >= fromIdx; --i) {
      if (values[i] == value) return i;
    }
    return -1;
  }

  public static void reverse(long[] values) {
    reverse(values, 0, values.length);
  }

  public static void reverse(long[] values, int fromIdx, int toIdx) {
    for (int i = fromIdx, j = toIdx - 1; i < j; ++i, --j) {
      swap(values, i, j);
    }
  }

  public static int unique(long[] values) {
    return unique(values, 0, values.length);
  }

  public static int unique(long[] values, int fromIdx, int toIdx) {
    if (fromIdx == toIdx) return 0;
    int res = 1;
    for (int i = fromIdx + 1; i < toIdx; ++i) {
      if (values[i - 1] != values[i]) {
        values[fromIdx + res++] = values[i];
      }
    }
    return res;
  }

  public static void sort(long[] values) {
    sort(values, 0, values.length);
  }

  public static void sort(long[] values, int fromIdx, int toIdx) {
    shuffle(values, fromIdx, toIdx);
    Arrays.sort(values, fromIdx, toIdx);
  }

  public static int sortAndUnique(long[] values) {
    return sortAndUnique(values, 0, values.length);
  }

  public static int sortAndUnique(long[] values, int fromIdx, int toIdx) {
    sort(values, fromIdx, toIdx);
    return unique(values, fromIdx, toIdx);
  }

  public static int unique(long[] values, int[] cnt) {
    return unique(values, 0, values.length, cnt, 0);
  }

  public static int unique(long[] values, int fromIdx, int toIdx, int[] cnt, int startIdx) {
    if (fromIdx == toIdx) return 0;
    int res = 1;
    cnt[startIdx] = 1;
    for (int i = fromIdx + 1; i < toIdx; ++i) {
      if (values[i - 1] != values[i]) {
        values[fromIdx + res++] = values[i];
        cnt[++startIdx] = 1;
      } else {
        ++cnt[startIdx];
      }
    }
    return res;
  }

  public static void update(long[] values, long delta) {
    update(values, 0, values.length, delta);
  }

  public static void update(long[] values, int fromIdx, int toIdx, long delta) {
    for (int i = fromIdx; i < toIdx; ++i) {
      values[i] += delta;
    }
  }

  public static int lowerBound(long[] values, long value) {
    return lowerBound(values, 0, values.length, value);
  }

  public static int lowerBound(long[] values, int fromIdx, int toIdx, long value) {
    int res = toIdx;
    for (int lower = fromIdx, upper = toIdx - 1; lower <= upper; ) {
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
    return upperBound(values, 0, values.length, value);
  }

  public static int upperBound(long[] values, int fromIdx, int toIdx, long value) {
    int res = toIdx;
    for (int lower = fromIdx, upper = toIdx - 1; lower <= upper; ) {
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

  public static void shuffle(long[] values) {
    shuffle(values, 0, values.length);
  }

  public static void shuffle(long[] values, int fromIdx, int toIdx) {
    for (int i = toIdx - fromIdx - 1; i > 0; --i) {
      swap(values, i + fromIdx, RANDOM.nextInt(i + 1) + fromIdx);
    }
  }

  /**
   * Returns {@code k}-th (0-indexed) smallest value.
   */
  public static long kth(long[] values, int kth) {
    return kthInternal(values, 0, values.length - 1, kth);
  }

  public static String toString(long[] values) {
    return toString(values, 0, values.length);
  }

  public static String toString(long[] values, int fromIdx, int toIdx) {
    StringBuilder sb = new StringBuilder("[");
    for (int i = fromIdx; i < toIdx; ++i) {
      if (i != fromIdx) sb.append(", ");
      sb.append(values[i]);
    }
    return sb.append("]").toString();
  }

  private static long kthInternal(long[] values, int lower, int upper, int kth) {
    if (lower == upper) return values[lower];
    if (lower + 1 == upper) {
      if (values[lower] > values[lower + 1]) swap(values, lower, lower + 1);
      return kth > 0 ? values[lower + 1] : values[lower];
    }
    long pivot = values[lower + RANDOM.nextInt(upper - lower)];
    int i = lower, j = upper;
    while (i <= j) {
      for ( ; values[i] < pivot; ++i) {}
      for ( ; values[j] > pivot; --j) {}
      if (i <= j) swap(values, i++, j--);
    }
    return kth < i - lower
        ? kthInternal(values, lower, i - 1, kth)
        : kthInternal(values, i, upper, kth - i + lower);
  }
}
