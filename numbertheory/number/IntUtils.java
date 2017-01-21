package template.numbertheory.number;

public class IntUtils {

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
}
