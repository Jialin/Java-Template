package template.numbertheory;

public class IntUtils {

  private static final int MOD = 1000000007;

  public static int add(int a, int b) {
    return add(a, b, MOD);
  }

  public static int add(int a, int b, int mod) {
    a += b;
    return a >= mod ? a - mod : a;
  }

  public static int mul(int a, int b) {
    return mul(a, b, MOD);
  }

  public static int mul(int a, int b, int mod) {
    return a > 0
        ? (
            b < mod / a
                ? a * b
                : (int) ((long) a * b % mod))
        : 0;
  }
}
