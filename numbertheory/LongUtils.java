package template.numbertheory;

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
}
