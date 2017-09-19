package template.numbertheory.hash;

public class HashUtils {

  public static long START_SEED = 100000000000000003L;
  public static long END_SEED = 1000000000000000003L;
  public static int BASE = 1000000007;

  public static long calc(char[] values) {
    return calc(values, 0, values.length);
  }

  public static long calc(char[] values, int fromIdx, int toIdx) {
    long res = START_SEED;
    for (int i = fromIdx; i < toIdx; ++i) {
      res = (i == fromIdx ? res : res * BASE) ^ hashSingle(values[i]);
    }
    return res * END_SEED;
  }

  public static long calc(long value) {
    return (START_SEED ^ value) * END_SEED;
  }

  public static long calc(long value1, long value2) {
    return ((START_SEED
        ^ calc(value1)) * BASE
        ^ calc(value2)) * END_SEED;
  }

  public static long calc(long[] values) {
    return calc(values, 0, values.length);
  }

  public static long calc(long[] values, int fromIdx, int toIdx) {
    long res = START_SEED;
    for (int i = fromIdx; i < toIdx; ++i) {
      res = (i == fromIdx ? res : res * BASE) ^ calc(values[i]);
    }
    return res * END_SEED;
  }

  public static long calcHashed(long[] hashedValues) {
    return calcHashed(hashedValues, 0, hashedValues.length);
  }

  public static long calcHashed(long[] hashedValues, int fromIdx, int toIdx) {
    long res = START_SEED;
    for (int i = fromIdx; i < toIdx; ++i) {
      res = (i == fromIdx ? res : res * BASE) ^ hashedValues[i];
    }
    return res * END_SEED;
  }

  private static long hashSingle(char value) {
    return (START_SEED ^ value) * END_SEED;
  }
}
