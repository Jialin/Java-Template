package template.numbertheory.number;

import template.array.IntArrayUtils;

public class IntUtils {

  public static int MOD = 1000000007;

  private static int[] x = new int[2];

  public static int add(int a, int b) {
    return add(a, b, MOD);
  }

  public static int add(int a, int b, int mod) {
    return slightFix(a + b, mod);
  }

  public static int sub(int a, int b) {
    return sub(a, b, MOD);
  }

  public static int sub(int a, int b, int mod) {
    return slightFix(a - b, mod);
  }

  public static int fix(int a) {
    return fix(a, MOD);
  }

  public static int fix(int a, int mod) {
    a = slightFix(a, mod);
    return 0 <= a && a < mod
        ? a
        : slightFix(a % mod, mod);
  }

  public static int mul(int a, int b) {
    return mul(a, b, MOD);
  }

  public static int mul(int a, int b, int mod) {
    return a > 0
        ? (b < mod / a
            ? a * b
            : (int) ((long) a * b % mod))
        : 0;
  }

  public static int pow(int a, long b) {
    return pow(a, b, MOD);
  }

  public static int pow(int a, long b, int mod) {
    int res = 1;
    for ( ; b > 0; b >>= 1, a = mul(a, a, mod)) if ((b & 1) > 0) res = mul(res, a, mod);
    return res >= mod ? fix(res, mod) : res;
  }

  public static int modInverse(int a) {
    return modInverse(a, MOD);
  }

  public static int modInverse(int a, int mod) {
    if (extGcd(a, mod, x) != 1) {
      throw new IllegalArgumentException();
    }
    return slightFix(x[0], mod);
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

  public static int[][] combination(int n) {
    int[][] res = new int[n][];
    for (int i = 0; i < n; ++i) {
      res[i] = new int[i + 1];
      res[i][0] = res[i][i] = 1;
      for (int j = 1; j < i; ++j) {
        res[i][j] = res[i - 1][j - 1] + res[i - 1][j];
      }
    }
    return res;
  }

  private static int slightFix(int a, int mod) {
    return a >= mod
        ? a - mod
        : a < 0
            ? a + mod
            : a;
  }
}
