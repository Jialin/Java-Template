package template.array;

public class LongArrayUtils {

  public static long min(long[] values) {
    long res = Long.MAX_VALUE;
    for (long value : values) {
      res = Math.min(res, value);
    }
    return res;
  }

  public static long max(long[] values) {
    long res = Long.MIN_VALUE;
    for (long value : values) {
      res = Math.max(res, value);
    }
    return res;
  }

  public static void reverse(long[] values) {
    for (int i = 0, j = values.length - 1; i < j; ++i, --j) {
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
}
