package template.collections.binaryindexedtree;

import java.util.Arrays;

public abstract class AbstractIntBinaryIndexedTree2D {

  private int n, m;
  private int[][] values;

  public AbstractIntBinaryIndexedTree2D(int capacity1, int capacity2) {
    values = new int[capacity1][capacity2];
  }

  public abstract int merge(int x, int y);

  public void init(int n, int m, int initValue) {
    this.n = n;
    this.m = m;
    for (int i = 0; i < n; ++i) {
      Arrays.fill(values[i], 0, m, initValue);
    }
  }

  public void update(int x, int y, int value) {
    for ( ; x < n; x |= x + 1) for (int i = y; i < m; i |= i + 1) {
      values[x][i] = merge(values[x][i], value);
    }
  }

  public int calc(int x, int y, int initValue) {
    int res = initValue;
    for ( ; x >= 0; x = (x & (x + 1)) - 1) for (int i = y; i >= 0; i = (i & (i + 1)) - 1) {
      res = merge(res, values[x][i]);
    }
    return res;
  }
}
