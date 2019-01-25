package template.array;

public class Long2DArrayUtils {

  public static void rotate90(int n, long[][] values) {
    for (int i = (n - 1) >> 1; i >= 0; --i) for (int j = (n >> 1) - 1; j >= 0; --j) {
      long tmp = values[i][j];
      values[i][j] = values[n - 1 - j][i];
      values[n - 1 - j][i] = values[n - 1 - i][n - 1 - j];
      values[n - 1 - i][n - 1 - j] = values[j][n - 1 - i];
      values[j][n - 1 - i] = tmp;
    }
  }

  public static void diagonalFlip(int n, int m, long[][] values) {
    if (n >= m) {
      for (int i = 0; i < n; ++i) for (int j = Math.min(i, m) - 1; j >= 0; --j) {
        swap(values, i, j, j, i);
      }
    } else {
      for (int i = 0; i < n; ++i) for (int j = i + 1; j < m; ++j) {
        swap(values, i, j, j, i);
      }
    }
  }

  public static void swap(long[][] values, int x1, int y1, int x2, int y2) {
    if (x1 != x2 || y1 != y2) {
      values[x1][y1] ^= values[x2][y2];
      values[x2][y2] ^= values[x1][y1];
      values[x1][y1] ^= values[x2][y2];
    }
  }

  public static boolean equals(int n, long[][] values1, long[][] values2) {
    for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) if (values1[i][j] != values2[i][j]) {
      return false;
    }
    return true;
  }

  public static String toDisplay(int n, int m, long[][] values) {
    StringBuilder sb = new StringBuilder("[\n");
    for (int i = 0; i < n; ++i) {
      sb.append(String.format("\t%s,\n", LongArrayUtils.toDisplay(values[i], 0, m)));
    }
    return sb.append(']').toString();
  }
}
