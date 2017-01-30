package template.numbertheory.number;

import template.array.IntArrayUtils;

public class IntModular {

  private static final int MOD = 1000000007;

  public final int mod;

  private final int[] x;

  public IntModular() {
    this(MOD);
  }

  public IntModular(int mod) {
    this.mod = mod;
    this.x = new int[2];
  }

  public int add(int a, int b) {
    return slightFix(a + b);
  }

  public int sub(int a, int b) {
    return slightFix(a - b);
  }

  public int fix(int a) {
    a = slightFix(a);
    return 0 <= a && a < mod ? a : slightFix(a % mod);
  }

  public int mul(int a, int b) {
    return a > 0
        ? (b < mod / a ? a * b : (int) ((long) a * b % mod))
        : 0;
  }

  public int pow(int a, long b) {
    int res = 1;
    for ( ; b > 0; b >>= 1, a = mul(a, a)) if ((b & 1) > 0) {
      res = mul(res, a);
    }
    return res >= mod ? fix(res) : res;
  }

  public int inverse(int a) {
    if (extGcd(a, mod, x) != 1) {
      throw new IllegalArgumentException();
    }
    return slightFix(x[0]);
  }

  public int gcd(int a, int b) {
    return b != 0 ? gcd(b, a % b) : a;
  }

  public int extGcd(int a, int b, int[] x) {
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

  private int slightFix(int a) {
    return a >= mod
        ? a - mod
        : a < 0 ? a + mod : a;
  }
}
