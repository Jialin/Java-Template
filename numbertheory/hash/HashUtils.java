package template.numbertheory.hash;

public class HashUtils {

  public static long START_SEED = 100000000000000003L;
  public static long END_SEED = 1000000000000000003L;
  public static int BASE = 1000000007;

  public static long calc(long[] values) {
    return calc(values, 0, values.length);
  }

  public static long calc(long[] values, int fromIdx, int toIdx) {
    long res = START_SEED;
    for (int i = fromIdx; i < toIdx; ++i) {
      res = (res * BASE) ^ values[i];
    }
    return res * END_SEED;
  }
}
