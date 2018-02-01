package template.numbertheory.number;

import template.array.LongArrayUtils;
import template.collections.list.IntArrayList;
import template.collections.list.LongArrayList;

import java.util.Random;

public class LongUtils {

  private static final int[] PRIMES = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23};
  private static final Random RANDOM = new Random(1000000000000000003L);

  private static final int MAX_PRIME = 64;
  private static final LongArrayList P = new LongArrayList(MAX_PRIME);
  private static final IntArrayList R = new IntArrayList(MAX_PRIME);

  public static boolean isPrime(long n) {
    if (n <= 1 || (n > 2 && (n & 1) == 0)) return false;
    int s = Long.numberOfTrailingZeros(n - 1);
    long d = (n - 1) >> s;
    for (int i = 0; i < PRIMES.length && PRIMES[i] < n; ++i) {
      long x = LongModular.pow(PRIMES[i], d, n);
      if (x == 1) continue;
      int r;
      for (r = 0; r < s; ++r) {
        if (x == n - 1) break;
        x = LongModular.mul(x, x, n);
      }
      if (r == s) return false;
    }
    return true;
  }

  /** Returns one of the divisor of {@code n}. Only returns 1 when {@code n} equals 1. */
  public static long divisor(long n) {
    if (n == 1) return 1;
    if (isPrime(n)) return n;
    long d = n;
    while (d == n) {
      long a = 1 + RANDOM.nextLong() % (n - 1);
      long b = 1 + RANDOM.nextLong() % (n - 1);
      long x = 2, y = 2;
      d = -1;
      while (d == 1 || d == -1) {
        x = pollardRhoFunc(x, n, a, b);
        y = pollardRhoFunc(pollardRhoFunc(y, n, a, b), n, a, b);
        d = gcd(x - y, n);
      }
    }
    return d < 0 ? -d : d;
  }

  /** Factorizes {@code n} into the form {@code p_0^r_0*...*p_(m-1)^r_(m-1)}. */
  public static void factorize(long n, LongArrayList p, IntArrayList r) {
    factorizeInternal(n);
    p.clear();
    r.clear();
    p.addAll(P);
    r.addAll(R);
  }

  /**
   * Factorizes {@code n} into the form {@code p_0^r_0*...*p_(m-1)^r_(m-1)}, and returns the number of different
   * prime factors.
   */
  public static int factorize(long n, long[] p, int[] r) {
    factorizeInternal(n);
    for (int i = P.size - 1; i >= 0; --i) {
      p[i] = P.get(i);
      r[i] = R.get(i);
    }
    return P.size;
  }

  /** Gets all divisors of {@code n}. */
  public static void divisors(long n, LongArrayList divisors) {
    factorizeInternal(n);
    divisors(P, R, divisors);
  }

  /** Gets all divisors of {@code n} which equals {@code {@code p_0^r_0*...*p_(m-1)^r_(m-1)}}. */
  public static void divisors(LongArrayList p, IntArrayList r, LongArrayList divisors) {
    divisors.clear();
    divisors.add(1);
    for (int i = 0; i < p.size; ++i) {
      long prime = p.get(i);
      int size = divisors.size;
      for (int remExp = r.get(i); remExp > 0; --remExp) {
        for (int j = divisors.size - size, k = 0; k < size; ++j, ++k) {
          divisors.add(divisors.get(j) * prime);
        }
      }
    }
  }

  /** Gets all divisors of {@code n}. */
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

  public static boolean isBitOne(long x, int bit) {
    return ((x >> bit) & 1) > 0;
  }

  public static boolean isBitZero(long x, int bit) {
    return ((x >> bit) & 1) == 0;
  }

  private static void factorizeInternal(long n) {
    P.clear();
    factorizeDfs(n);
    P.sortAndUnique(R);
  }

  private static void factorizeDfs(long n) {
    long divisor = divisor(n);
    if (divisor == n) {
      if (divisor > 1) P.add(divisor);
      return;
    }
    factorizeDfs(divisor);
    factorizeDfs(n / divisor);
  }

  private static long pollardRhoFunc(long x, long n, long a, long b) {
    return LongModular.mul(x, x + b, n) + a;
  }
}
