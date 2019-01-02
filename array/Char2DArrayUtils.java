package template.array;

public class Char2DArrayUtils {

  public static void rotate90(int n, char[][] values) {
    for (int i = (n - 1) >> 1; i >= 0; --i) {
      for (int j = (n >> 1) - 1; j >= 0; --j) {
        char tmp = values[i][j];
        values[i][j] = values[n - 1 - j][i];
        values[n - 1 - j][i] = values[n - 1 - i][n - 1 - j];
        values[n - 1 - i][n - 1 - j] = values[j][n - 1 - i];
        values[j][n - 1 - i] = tmp;
      }
    }
  }

  public static boolean equals(int n, char[][] s, char[][] t) {
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (s[i][j] != t[i][j]) {
          return false;
        }
      }
    }
    return true;
  }
}
