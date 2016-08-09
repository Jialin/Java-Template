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
}
