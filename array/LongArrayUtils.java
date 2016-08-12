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
