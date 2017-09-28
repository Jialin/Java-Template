package template.array;

public class BooleanArrayUtils {

  public static String toDisplay(boolean[] values) {
    return toDisplay(values, 0, values.length);
  }

  public static String toDisplay(boolean[] values, int fromIdx, int toIdx) {
    StringBuilder sb = new StringBuilder("[");
    for (int i = fromIdx; i < toIdx; ++i) {
      sb.append(values[i] ? 'Y' : 'N');
    }
    return sb.append("]").toString();
  }

  public static boolean[] expand(boolean[] values, int newLength) {
    if (values != null && values.length >= newLength) return values;
    boolean[] res = new boolean[newLength];
    if (values == null) return res;
    for (int i = 0; i < values.length; ++i) res[i] = values[i];
    return res;
  }
}
