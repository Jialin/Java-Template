package template.numbertheory.matrix;

import template.numbertheory.number.IntUtils;

public class LongMatrixUtils {

  private static long[][] tmp;

  public static void initZero(long[][] a) {
    initZero(a.length, a[0].length, a);
  }

  public static void initZero(int n, int m, long[][] a) {
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
      a[i][j] = 0;
    }
  }

  public static void initEye(long[][] a) {
    initEye(a.length, a);
  }

  public static void initEye(int n, long[][] a) {
    for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) {
      a[i][j] = i == j ? 1 : 0;
    }
  }

  public static void initFib(int[][] a) {
    a[0][0] = 0; a[0][1] = 1;
    a[1][0] = 1; a[1][1] = 1;
  }

  public static void mul(long[][] res, long[][] a, long[][] b) {
    mul(res, a.length, a[0].length, a, b.length, b[0].length, b);
  }

  public static void mul(long[][] res, int n, long[][] a, long[][] b) {
    mul(res, n, n, a, n, n, b);
  }

  public static void mul(long[][] res, int na, int ma, long[][] a, int nb, int mb, long[][] b) {
    long[][] c;
    if (res != a && res != b) {
      c = res;
    } else {
      if (tmp == null || tmp.length < na || tmp[0].length < mb) {
        tmp = new long[IntUtils.nextPow2(na)][IntUtils.nextPow2(mb)];
      }
      c = tmp;
    }
    for (int i = 0; i < na; ++i) for (int j = 0; j < mb; ++j) {
      long value = 0;
      for (int k = 0; k < ma; ++k) value += a[i][k] * b[k][j];
      c[i][j] = value;
    }
    if (res != c) {
      for (int i = 0; i < na; ++i) for (int j = 0; j < mb; ++j) {
        res[i][j] = c[i][j];
      }
    }
  }

  public static void pow(long[][] res, long[][][] bases, long exp) {
    pow(res, bases[0].length, bases, exp);
  }

  public static void pow(long[][] res, int n, long[][][] bases, long exp) {
    initEye(n, res);
    for (int i = 0; exp > 0; ++i, exp >>= 1) if ((exp & 1) > 0) {
      mul(res, n, res, bases[i]);
    }
  }
}
