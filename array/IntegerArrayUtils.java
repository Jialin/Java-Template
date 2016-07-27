package template.array;

public class IntegerArrayUtils {

  public static Integer[] range(int to) {
    return range(0, to);
  }

  public static Integer[] range(int from, int to) {
    return range(from, to, 1);
  }

  public static Integer[] range(int from, int to, int step) {
    if (from >= to) return new Integer[0];
    Integer[] res = new Integer[(to - from + step - 1) / step];
    for (int i = 0, j = from; j < to; ++i, j += step) {
      res[i] = j;
    }
    return res;
  }
}
