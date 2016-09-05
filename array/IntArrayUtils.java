package template.array;

public class IntArrayUtils {

  public static int min(int[] values) {
    int res = Integer.MAX_VALUE;
    for (int value : values) {
      res = Math.min(res, value);
    }
    return res;
  }

  public static int max(int[] values) {
    int res = Integer.MIN_VALUE;
    for (int value : values) {
      res = Math.max(res, value);
    }
    return res;
  }

  public static void reverse(int[] values) {
    for (int i = 0, j = values.length - 1; i < j; ++i, --j) {
      values[i] ^= values[j];
      values[j] ^= values[i];
      values[i] ^= values[j];
    }
  }

  public static int unique(int[] values) {
    if (values.length == 0) return 0;
    int res = 1;
    for (int i = 1; i < values.length; ++i) {
      if (values[i - 1] != values[i]) {
        values[res++] = values[i];
      }
    }
    return res;
  }

  public static int lowerBound(int[] values, int value) {
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

  public static int upperBound(int[] values, int value) {
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
}
