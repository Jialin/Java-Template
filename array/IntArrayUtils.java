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
}
