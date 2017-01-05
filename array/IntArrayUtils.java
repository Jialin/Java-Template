package template.array;

import java.util.Arrays;
import java.util.Random;

public class IntArrayUtils {
  private static final Random RANDOM = new Random(1000000007);

  public static int min(int[] values) {
    return min(values, 0, values.length);
  }

  public static int min(int[] values, int fromIdx, int toIdx) {
    int res = Integer.MAX_VALUE;
    for (int i = fromIdx; i < toIdx; ++i) {
      if (res > values[i]) res = values[i];
    }
    return res;
  }

  public static int max(int[] values) {
    return max(values, 0, values.length);
  }

  public static int max(int[] values, int fromIdx, int toIdx) {
    int res = Integer.MIN_VALUE;
    for (int i = fromIdx; i < toIdx; ++i) {
      if (res < values[i]) res = values[i];
    }
    return res;
  }

  public static int find(int[] values, int value) {
    return find(values, 0, values.length, value);
  }

  public static int find(int[] values, int fromIdx, int toIdx, int value) {
    for (int i = fromIdx; i < toIdx; ++i) {
      if (values[i] == value) return i;
    }
    return -1;
  }

  public static int findLast(int[] values, int value) {
    return find(values, 0, values.length, value);
  }

  public static int findLast(int[] values, int fromIdx, int toIdx, int value) {
    for (int i = toIdx - 1; i >= fromIdx; --i) {
      if (values[i] == value) return i;
    }
    return -1;
  }

  public static void reverse(int[] values) {
    reverse(values, 0, values.length);
  }

  public static void reverse(int[] values, int fromIdx, int toIdx) {
    for (int i = fromIdx, j = toIdx - 1; i < j; ++i, --j) {
      values[i] ^= values[j];
      values[j] ^= values[i];
      values[i] ^= values[j];
    }
  }

  public static int unique(int[] values) {
    return unique(values, 0, values.length);
  }

  public static int unique(int[] values, int fromIdx, int toIdx) {
    if (fromIdx == toIdx) return 0;
    int res = 1;
    for (int i = fromIdx + 1; i < toIdx; ++i) {
      if (values[i - 1] != values[i]) {
        values[fromIdx + res++] = values[i];
      }
    }
    return res;
  }

  public static int sortAndUnique(int[] values) {
    return sortAndUnique(values, 0, values.length);
  }

  public static int sortAndUnique(int[] values, int fromIdx, int toIdx) {
    Arrays.sort(values, fromIdx, toIdx);
    return unique(values, fromIdx, toIdx);
  }

  public static int unique(int[] values, int[] cnt) {
    return unique(values, 0, values.length, cnt, 0);
  }

  public static int unique(int[] values, int fromIdx, int toIdx, int[] cnt, int startIdx) {
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

  public static void update(int[] values, int delta) {
    update(values, 0, values.length, delta);
  }

  public static void update(int[] values, int fromIdx, int toIdx, int delta) {
    for (int i = fromIdx; i < toIdx; ++i) {
      values[i] += delta;
    }
  }

  public static int lowerBound(int[] values, int value) {
    return lowerBound(values, 0, values.length, value);
  }

  public static int lowerBound(int[] values, int fromIdx, int toIdx, int value) {
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

  public static int upperBound(int[] values, int value) {
    return upperBound(values, 0, values.length, value);
  }

  public static int upperBound(int[] values, int fromIdx, int toIdx, int value) {
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

  public static void swap(int[] values, int uIdx, int vIdx) {
    if (uIdx == vIdx) return;
    values[uIdx] ^= values[vIdx];
    values[vIdx] ^= values[uIdx];
    values[uIdx] ^= values[vIdx];
  }

  public static void shuffle(int[] values) {
    shuffle(values, 0, values.length);
  }

  public static void shuffle(int[] values, int lower, int upper) {
    for (int i = upper - lower - 1; i > 0; --i) {
      swap(values, i + lower, RANDOM.nextInt(i + 1) + lower);
    }
  }

  /**
   * Returns {@code k}-th (0-indexed) smallest value.
   */
  public static int kth(int[] values, int kth) {
    return kthInternal(values, 0, values.length - 1, kth);
  }

  public static String toString(int[] values) {
    return toString(values, 0, values.length);
  }

  public static String toString(int[] values, int fromIdx, int toIdx) {
    StringBuilder sb = new StringBuilder("[");
    for (int i = fromIdx; i < toIdx; ++i) {
      if (i != fromIdx) sb.append(", ");
      sb.append(values[i]);
    }
    return sb.append("]").toString();
  }

  private static int kthInternal(int[] values, int lower, int upper, int kth) {
    if (lower == upper) return values[lower];
    if (lower + 1 == upper) {
      if (values[lower] > values[lower + 1]) swap(values, lower, lower + 1);
      return kth > 0 ? values[lower + 1] : values[lower];
    }
    int pivot = values[lower + RANDOM.nextInt(upper - lower)];
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
