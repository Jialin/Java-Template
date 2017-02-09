package template.numbertheory.number;

public class LongModular {

  public static long fix(long a, long mod) {
    a = slightFix(a, mod);
    return 0 <= a && a < mod ? a : slightFix(a % mod, mod);
  }

  public static long add(long a, long b, long mod) {
    return slightFix(fix(a, mod) + fix(b, mod), mod);
  }

  public static long mul(long a, long b, long mod) {
    a = fix(a, mod);
    b = fix(b, mod);
    long res = 0;
    for ( ; b > 0; b >>= 1, a = add(a, a, mod)) if ((b & 1) > 0) {
      res = add(res, a, mod);
    }
    return res;
  }

  public static long pow(long a, long b, long mod) {
    a = fix(a, mod);
    long res = 1;
    for ( ; b > 0; b >>= 1, a = mul(a, a, mod)) if ((b & 1) > 0) {
      res = mul(res, a, mod);
    }
    return res < mod ? res : fix(res, mod);
  }

  private static long slightFix(long a, long mod) {
    return a >= mod
        ? a - mod
        : a < 0 ? a + mod : a;
  }
}
