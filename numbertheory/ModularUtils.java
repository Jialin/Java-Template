package template.numbertheory;

public class ModularUtils {

  private static final int MOD = 1000000007;

  /**
   * Calculates {@code (a + b) % MOD}.
   */
  public static int add(int a, int b) {
    return add(a, b, MOD);
  }

  /**
   * Calculates {@code (a + b) % mod}.
   */
  public static int add(int a, int b, int mod) {
    a += b;
    return a >= mod ? a - mod : a;
  }

  /**
   * Calculates {@code a * b % MOD}.
   */
  public static int mul(int a, int b) {
    return mul(a, b, MOD);
  }

  /**
   * Calculates {@code a * b % mod}.
   */
  public static int mul(int a, int b, int mod) {
    return a > 0
        ? (
            b < mod / a
                ? a * b
                : (int) ((long) a * b % mod))
        : 0;
  }
}
