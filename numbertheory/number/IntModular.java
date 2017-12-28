package template.numbertheory.number;

import template.array.IntArrayUtils;
import template.collections.list.IntArrayList;

public class IntModular {

  private static final int MOD = 1000000007;

  public final int mod;

  private final int[] x;
  private IntArrayList phis;

  public IntModular() {
    this(MOD);
  }

  public IntModular(int mod) {
    this.mod = mod;
    this.x = new int[2];
  }

  public int add(int a, int b) {
    return fix(a + b, mod);
  }

  public int sub(int a, int b) {
    return fix(a - b, mod);
  }

  public int fix(int a) {
    a = slightFix(a, mod);
    return 0 <= a && a < mod ? a : slightFix(a % mod, mod);
  }

  public int fix(long a) {
    return fix((int) (a % mod), mod);
  }

  public int mul(int a, int b) {
    return mul(a, b, mod);
  }

  public int div(int a, int b) {
    return mul(a, inverse(b), mod);
  }

  public int pow(int a, long b) {
    return pow(a, b, mod);
  }

  public int pow(int a, int b) {
    return pow(a, b, mod);
  }

  public int powTower(IntArrayList bases) {
    if (phis == null) {
      phis = new IntArrayList(32);
      phis.add(mod);
    }
    for ( ; phis.size < bases.size && phis.peekLast() > 1; phis.add(IntUtils.phi(phis.peekLast()))) {}
    int exp = 1;
    for (int i = Math.min(phis.size, bases.size) - 1; i >= 0; --i) {
      exp = pow(bases.get(i), exp, phis.get(i));
    }
    return fix(exp, phis.get(0));
  }

  public int inverse(int a) {
    if (extGcd(a, mod, x) != 1) {
      throw new IllegalArgumentException();
    }
    return slightFix(x[0], mod);
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

  public static int mul(int a, int b, int mod) {
    return a > 0
        ? (b < mod / a ? a * b : (int) ((long) a * b % mod))
        : 0;
  }

  public static int fix(int a, int mod) {
    a = slightFix(a, mod);
    return 0 <= a && a < mod ? a : slightFix(a % mod, mod);
  }

  public static int pow(int a, long b, int mod) {
    int res = 1;
    for ( ; b > 0; b >>= 1, a = mul(a, a, mod)) if ((b & 1) > 0) {
      res = mul(res, a, mod);
    }
    return res >= mod ? fix(res, mod) : res;
  }

  public static int pow(int a, int b, int mod) {
    int res = 1;
    for ( ; b > 0; b >>= 1, a = mul(a, a, mod)) if ((b & 1) > 0) {
      res = mul(res, a, mod);
    }
    return res >= mod ? fix(res, mod) : res;
  }

  /**
   * If <code>a**b < mod</code>, return <code>a**b</code>. Otherwise return <code>a**b % mod + mod</code>.
   *
   * Used to calculate power tower.
   */
  public static int powPlus(int a, int b, int mod) {
    if (b == 0) return 1;
    int res = 1;
    boolean exceed = a >= mod;
    a = fix(a, mod);
    while (b > 0) {
      if ((b & 1) > 0) {
        exceed |= a > 0 && res >= mod / a;
        res = mul(res, a, mod);
      }
      b >>= 1;
      if (b > 0) {
        exceed |= a > 0 && a >= mod / a;
        a = mul(a, a, mod);
      }
    }
    return res + (exceed ? mod : 0);
  }

  private static int slightFix(int a, int mod) {
    return a >= mod
        ? a - mod
        : a < 0 ? a + mod : a;
  }
}
