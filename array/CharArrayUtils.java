package template.array;

import java.util.Arrays;
import java.util.Random;

public class CharArrayUtils {

  private static final Random RANDOM = new Random(1000000007);

  public static void fillRange(char[] values, char end) {
    fillRange(values, (char) 0, end);
  }

  public static void fillRange(char[] values, char start, char end) {
    char value = start;
    for (int i = 0; value < end; ++i, ++value) {
      values[i] = value;
    }
  }

  public static char min(char[] values) {
    return min(values, 0, values.length);
  }

  public static char min(char[] values, int fromIdx, int toIdx) {
    char res = Character.MAX_VALUE;
    for (int i = fromIdx; i < toIdx; ++i) {
      if (res > values[i]) res = values[i];
    }
    return res;
  }

  public static char max(char[] values) {
    return max(values, 0, values.length);
  }

  public static char max(char[] values, int fromIdx, int toIdx) {
    char res = Character.MIN_VALUE;
    for (int i = fromIdx; i < toIdx; ++i) {
      if (res < values[i]) res = values[i];
    }
    return res;
  }

  public static int find(char[] values, char value) {
    return find(values, 0, values.length, value);
  }

  public static int find(char[] values, int fromIdx, int toIdx, char value) {
    for (int i = fromIdx; i < toIdx; ++i) {
      if (values[i] == value) return i;
    }
    return -1;
  }

  public static int findLast(char[] values, char value) {
    return find(values, 0, values.length, value);
  }

  public static int findLast(char[] values, int fromIdx, int toIdx, char value) {
    for (int i = toIdx - 1; i >= fromIdx; --i) {
      if (values[i] == value) return i;
    }
    return -1;
  }

  public static void reverse(char[] values) {
    reverse(values, 0, values.length);
  }

  public static void reverse(char[] values, int fromIdx, int toIdx) {
    for (int i = fromIdx, j = toIdx - 1; i < j; ++i, --j) {
      swap(values, i, j);
    }
  }

  public static boolean isPalindrome(char[] values) {
    return isPalindrome(values, 0, values.length);
  }

  public static boolean isPalindrome(char[] values, int fromIdx, int toIdx) {
    for (int i = fromIdx, j = toIdx - 1; i < j; ++i, --j) {
      if (values[i] != values[j]) {
        return false;
      }
    }
    return true;
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

  public static void sort(char[] values) {
    sort(values, 0, values.length);
  }

  public static void sort(char[] values, int fromIdx, int toIdx) {
    shuffle(values, fromIdx, toIdx);
    Arrays.sort(values, fromIdx, toIdx);
  }

  public static void sort(char[] primary, char[]... secondaries) {
    sort(primary, 0, primary.length, secondaries);
  }

  public static void sort(char[] primary, int fromIdx, int toIdx, char[]... secondaries) {
    if (fromIdx + 1 >= toIdx) {
      return;
    }
    int pivotIdx = fromIdx + RANDOM.nextInt(toIdx - fromIdx);
    int i = fromIdx;
    int j = toIdx - 1;
    while (i <= j) {
      for ( ; compare(primary, i, pivotIdx, secondaries) < 0; ++i) {}
      for ( ; compare(primary, j, pivotIdx, secondaries) > 0; --j) {}
      if (i <= j) {
        if (i == pivotIdx) {
          pivotIdx = j;
        } else if (j == pivotIdx) {
          pivotIdx = i;
        }
        for (char[] secondary : secondaries) {
          swap(secondary, i, j);
        }
        swap(primary, i++, j--);
      }
    }
    sort(primary, fromIdx, i, secondaries);
    sort(primary, i, toIdx, secondaries);
  }

  public static int sortAndUnique(char[] values) {
    return sortAndUnique(values, 0, values.length);
  }

  public static int sortAndUnique(char[] values, int fromIdx, int toIdx) {
    sort(values, fromIdx, toIdx);
    return unique(values, fromIdx, toIdx);
  }

  public static int unique(char[] values, int[] cnt) {
    return unique(values, 0, values.length, cnt, 0);
  }

  public static int unique(char[] values, int fromIdx, int toIdx, int[] cnt) {
    return unique(values, fromIdx, toIdx, cnt, 0);
  }

  public static int unique(char[] values, int fromIdx, int toIdx, int[] cnt, int startIdx) {
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

  public static void decreaseOne(char[] values) {
    decreaseOne(values, 0, values.length);
  }

  public static void decreaseOne(char[] values, int fromIdx, int toIdx) {
    for (int i = fromIdx; i < toIdx; ++i) {
      --values[i];
    }
  }

  public static void update(char[] values, char delta) {
    update(values, 0, values.length, delta);
  }

  public static void update(char[] values, int fromIdx, int toIdx, char delta) {
    for (int i = fromIdx; i < toIdx; ++i) {
      values[i] += delta;
    }
  }

  public static void replaceAll(char[] values, char oldValue, char newValue) {
    replaceAll(values, 0, values.length, oldValue, newValue);
  }

  public static void replaceAll(char[] values, int fromIdx, int toIdx, char oldValue, char newValue) {
    for (int i = fromIdx; i < toIdx; ++i) {
      if (values[i] == oldValue) {
        values[i] = newValue;
      }
    }
  }

  public static int lowerBound(char[] values, char value) {
    return lowerBound(values, 0, values.length, value);
  }

  public static int lowerBound(char[] values, int fromIdx, int toIdx, char value) {
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

  public static int upperBound(char[] values, char value) {
    return upperBound(values, 0, values.length, value);
  }

  public static int upperBound(char[] values, int fromIdx, int toIdx, char value) {
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

  public static void swap(char[] values, int uIdx, int vIdx) {
    if (uIdx == vIdx) return;
    values[uIdx] ^= values[vIdx];
    values[vIdx] ^= values[uIdx];
    values[uIdx] ^= values[vIdx];
  }

  public static void shuffle(char[] values) {
    shuffle(values, 0, values.length);
  }

  public static void shuffle(char[] values, int fromIdx, int toIdx) {
    for (int i = toIdx - fromIdx - 1; i > 0; --i) {
      swap(values, i + fromIdx, RANDOM.nextInt(i + 1) + fromIdx);
    }
  }

  public static char kth(char[] values, int kth) {
    return kth(values, 0, values.length, kth);
  }

  public static char kth(char[] values, int fromIdx, int toIdx, int kth) {
    return kthInternal(values, fromIdx, toIdx - 1, kth);
  }

  public static char[] expand(char[] values, int newLength) {
    if (values != null && values.length >= newLength) return values;
    char[] res = new char[newLength];
    if (values == null) return res;
    for (int i = 0; i < values.length; ++i) res[i] = values[i];
    return res;
  }

  public static boolean nextPermutation(char[] values) {
    return nextPermutation(values, 0, values.length);
  }

  public static boolean nextPermutation(char[] values, int fromIdx, int toIdx) {
    int pos = -1;
    for (int i = toIdx - 2; i >= fromIdx; --i) if (values[i] < values[i + 1]) {
      pos = i;
      break;
    }
    if (pos < 0) return false;
    int newPos = -1;
    for (int i = toIdx - 1; i > pos; --i) if (values[pos] < values[i]) {
      newPos = i;
      break;
    }
    swap(values, pos, newPos);
    reverse(values, pos + 1, toIdx);
    return true;
  }

  public static String toDisplay(char[] values) {
    return toDisplay(values, 0, values.length);
  }

  public static String toDisplay(char[] values, int fromIdx, int toIdx) {
    StringBuilder sb = new StringBuilder("[");
    for (int i = fromIdx; i < toIdx; ++i) {
      if (i != fromIdx) sb.append(", ");
      sb.append(values[i]);
    }
    return sb.append("]").toString();
  }

  private static void sortInternal(char[] values, int lower, int upper, char[]... attributes) {
    if (lower >= upper) {
      return;
    }
    char pivot = values[lower + RANDOM.nextInt(upper - lower + 1)];
    int i = lower;
    int j = upper;
    while (i <= j) {
      for ( ; values[i] < pivot; ++i) {}
      for ( ; values[j] > pivot; --j) {}
      if (i <= j) {
        for (char[] attribute : attributes) {
          swap(attribute, i, j);
        }
        swap(values, i++, j--);
      }
    }
    sortInternal(values, lower, i - 1, attributes);
    sortInternal(values, i, upper, attributes);
  }

  private static char kthInternal(char[] values, int lower, int upper, int kth) {
    if (lower == upper) {
      return values[lower];
    }
    if (lower + 1 == upper) {
      if (values[lower] > values[lower + 1]) {
        swap(values, lower, lower + 1);
      }
      return kth > 0 ? values[lower + 1] : values[lower];
    }
    // TODO(jouyang): maybe upper-lower+1 ??? and get rid of the special treatment above???
    char pivot = values[lower + RANDOM.nextInt(upper - lower)];
    int i = lower;
    int j = upper;
    while (i <= j) {
      for ( ; values[i] < pivot; ++i) {}
      for ( ; values[j] > pivot; --j) {}
      if (i <= j) {
        swap(values, i++, j--);
      }
    }
    return kth < i - lower
        ? kthInternal(values, lower, i - 1, kth)
        : kthInternal(values, i, upper, kth - i + lower);
  }


  private static int compare(char[] primary, int x, int y, char[]... secondaries) {
    if (primary[x] < primary[y]) {
      return -1;
    }
    if (primary[x] > primary[y]) {
      return 1;
    }
    for (char[] secondary : secondaries) {
      if (secondary[x] < secondary[y]) {
        return -1;
      }
      if (secondary[x] > secondary[y]) {
        return 1;
      }
    }
    return 0;
  }
}
