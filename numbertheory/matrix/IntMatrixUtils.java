package template.numbertheory.matrix;

import template.numbertheory.number.IntUtils;

public class IntMatrixUtils {

  private static int[][] tmp;

  public static void initZero(int[][] a) {
    initZero(a.length, a[0].length, a);
  }

  public static void initZero(int n, int m, int[][] a) {
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
      a[i][j] = 0;
    }
  }

  public static void initEye(int[][] a) {
    initEye(a.length, a);
  }

  public static void initEye(int n, int[][] a) {
    for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) {
      a[i][j] = i == j ? 1 : 0;
    }
  }

  public static void initFib(int[][] a) {
    a[0][0] = 0; a[0][1] = 1;
    a[1][0] = 1; a[1][1] = 1;
  }

  public static void mul(int[][] a, int[][] b, int[][] res) {
    mul(a.length, a[0].length, a, b.length, b[0].length, b, res);
  }

  public static void mul(int n, int[][] a, int[][] b, int[][] res) {
    mul(n, n, a, n, n, b, res);
  }

  public static void mul(int na, int ma, int[][] a, int nb, int mb, int[][] b, int[][] res) {
    int[][] c;
    if (res != a && res != b) {
      c = res;
    } else {
      if (tmp == null || tmp.length < na || tmp[0].length < mb) {
        tmp = new int[na][mb];
      }
      c = tmp;
    }
    for (int i = 0; i < na; ++i) for (int j = 0; j < mb; ++j) {
      long value = 0;
      for (int k = 0; k < ma; ++k) {
        long deltaValue = (long) a[i][k] * b[k][j];
        if (deltaValue <= Long.MAX_VALUE - value) {
          value += deltaValue;
        } else {
          value = value % IntUtils.MOD + deltaValue;
        }
      }
      c[i][j] = (int) (value % IntUtils.MOD);
    }
    if (res != c) {
      for (int i = 0; i < na; ++i) for (int j = 0; j < mb; ++j) {
        res[i][j] = c[i][j];
      }
    }
  }

  public static void pow(int[][][] bases, long exp, int[][] res) {
    pow(bases[0].length, bases, exp, res);
  }

  public static void pow(int n, int[][][] bases, long exp, int[][] res) {
    initEye(n, res);
    for (int i = 0; exp > 0; ++i, exp >>= 1) if ((exp & 1) > 0) {
      mul(n, res, bases[i], res);
    }
  }
}
