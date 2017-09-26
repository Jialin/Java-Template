package template.array;

public class BooleanArrayUtils {

  public static boolean[] expand(boolean[] values, int newLength) {
    if (values != null && values.length >= newLength) return values;
    boolean[] res = new boolean[newLength];
    if (values == null) return res;
    for (int i = 0; i < values.length; ++i) res[i] = values[i];
    return res;
  }
}
