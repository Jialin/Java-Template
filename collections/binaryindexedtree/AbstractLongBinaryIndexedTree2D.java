package template.collections.binaryindexedtree;

import java.util.Arrays;

public abstract class AbstractLongBinaryIndexedTree2D {

  private int n, m;
  private long[][] values;

  public AbstractLongBinaryIndexedTree2D(int capacity1, int capacity2) {
    values = new long[capacity1][capacity2];
  }

  public abstract long merge(long x, long y);

  public void init(int n, int m, long initValue) {
    this.n = n;
    this.m = m;
    for (int i = 0; i < n; ++i) {
      Arrays.fill(values[i], 0, m, initValue);
    }
  }

  public void update(int x, int y, long value) {
    for ( ; x < n; x |= x + 1) for (int i = y; i < m; i |= i + 1) {
      values[x][i] = merge(values[x][i], value);
    }
  }

  public long calc(int x, int y, long initValue) {
    long res = initValue;
    for ( ; x >= 0; x = (x & (x + 1)) - 1) for (int i = y; i >= 0; i = (i & (i + 1)) - 1) {
      res = merge(res, values[x][i]);
    }
    return res;
  }
}
