package template.numbertheory;

import template.array.IntArrayUtils;

public class IntUtils {

  private static final int MOD = 1000000007;

  public static int add(int a, int b) {
    return add(a, b, MOD);
  }

  public static int add(int a, int b, int mod) {
    a += b;
    return a >= mod ? a - mod : a;
  }

  public static int sub(int a, int b) {
    return sub(a, b, MOD);
  }

  public static int sub(int a, int b, int mod) {
    a -= b;
    return a >= 0 ? a + mod : a;
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

  public static int pow(int a, int b) {
    return pow(a, b, MOD);
  }

  public static int pow(int a, int b, int mod) {
    int res = 1;
    for ( ; b > 0; b >>= 1, a = mul(a, a, mod)) if ((b & 1) > 0) res = mul(res, a);
    return res;
  }

  public static int gcd(int a, int b) {
    return b != 0 ? gcd(b, a % b) : a;
  }

  public static int extGcd(int a, int b, int[] x) {
    if (b == 0) {
      x[0] = 1;
      x[1] = 0;
      return a;
    }
    int gcd = extGcd(b, a % b, x);
    IntArrayUtils.swap(x, 0, 1);
    x[1] -= a / b * x[0];
    return gcd;
  }
}
