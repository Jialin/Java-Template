package template.array;

public class Char2DArrayUtils {

  public static void rotate90(int n, char[][] values) {
    for (int i = (n - 1) >> 1; i >= 0; --i) for (int j = (n >> 1) - 1; j >= 0; --j) {
      char tmp = values[i][j];
      values[i][j] = values[n - 1 - j][i];
      values[n - 1 - j][i] = values[n - 1 - i][n - 1 - j];
      values[n - 1 - i][n - 1 - j] = values[j][n - 1 - i];
      values[j][n - 1 - i] = tmp;
    }
  }

  public static void diagonalFlip(int n, int m, char[][] values) {
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

  public static void swap(char[][] values, int x1, int y1, int x2, int y2) {
    if (x1 != x2 || y1 != y2) {
      values[x1][y1] ^= values[x2][y2];
      values[x2][y2] ^= values[x1][y1];
      values[x1][y1] ^= values[x2][y2];
    }
  }

  public static boolean equals(int n, char[][] values1, char[][] values2) {
    for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) if (values1[i][j] != values2[i][j]) {
      return false;
    }
    return true;
  }

  public static String toDisplay(int n, int m, char[][] values) {
    StringBuilder sb = new StringBuilder("[\n");
    for (int i = 0; i < n; ++i) {
      sb.append(String.format("\t%s,\n", CharArrayUtils.toDisplay(values[i], 0, m)));
    }
    return sb.append(']').toString();
  }
}
