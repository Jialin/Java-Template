package template.numbertheory.number;

public class IntUtils {

  public static boolean isPow2(int n) {
    return n > 0 && (n & (n - 1)) == 0;
  }

  public static int nextPow2(int n) {
    if (n < 1) return 1;
    return isPow2(n) ? n : Integer.highestOneBit(n) << 1;
  }

  public static boolean isPrime(int n) {
    if (n < 2) return false;
    for (int x = 2; x <= n / x; ++x) if (n % x == 0) {
      return false;
    }
    return true;
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

  public static boolean isEven(int x) {
    return (x & 1) == 0;
  }

  public static boolean isOdd(int x) {
    return (x & 1) > 0;
  }

  public static int gcd(int a, int b) {
    return b != 0 ? gcd(b, a % b) : a;
  }

  public static int lcm(int a, int b) {
    return a / gcd(a, b) * b;
  }

  public static int phi(int n) {
    int res = 1;
    for (int i = 2; i <= n / i; ++i) if (n % i == 0) {
      res *= i - 1;
      n /= i;
      for ( ; n % i == 0; res *= i, n /= i) {}
    }
    if (n > 1) res *= n - 1;
    return res;
  }
}
