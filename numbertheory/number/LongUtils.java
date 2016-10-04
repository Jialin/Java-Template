package template.numbertheory.number;

import template.array.LongArrayUtils;

public class LongUtils {

  /**
   * Factorizes {@code n} into the form {@code p_0^r_0*...*p_(m-1)^r_(m-1)}, and returns the number of different
   * prime factors.
   */
  public static int factorize(long n, long[] p, int[] r) {
    int res = 0;
    for (long i = 2; i <= n / i; ++i) if (n % i == 0) {
      int rCnt = 1;
      for (n /= i; n % i == 0; n /= i, ++rCnt) {}
      p[res] = i;
      r[res++] = rCnt;
    }
    if (n > 1) {
      p[res] = n;
      r[res++] = 1;
    }
    return res;
  }

  /**
   * Gets all divisors of {@code n}.
   */
  public static int divisors(long n, long[] divisors) {
    int res = 0;
    divisors[res++] = 1;
    for (long i = 2; i <= n / i; ++i) if (n % i == 0) {
      divisors[res++] = i;
    }
    int pnt = res - 1;
    if (divisors[pnt] != n / divisors[pnt]) {
      divisors[res++] = n / divisors[pnt];
    }
    for (int i = --pnt; i >= 0; i--) {
      divisors[res++] = n / divisors[i];
    }
    return res;
  }

  public static long gcd(long a, long b) {
    return b != 0 ? gcd(b, a % b) : a;
  }

  public static long extGcd(long a, long b, long[] x) {
    if (b == 0) {
      x[0] = 1;
      x[1] = 0;
      return a;
    }
    long gcd = extGcd(b, a % b, x);
    LongArrayUtils.swap(x, 0, 1);
    x[1] -= a / b * x[0];
    return gcd;
  }
}
