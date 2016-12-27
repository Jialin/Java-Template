package template.collections.binaryindexedtree;

import java.util.Arrays;

public abstract class AbstractIntBinaryIndexedTree {

  private int n;
  private int[] values;

  public AbstractIntBinaryIndexedTree(int capacity) {
    values = new int[capacity];
  }

  public abstract int merge(int x, int y);

  public void init(int n, int initValue) {
    this.n = n;
    Arrays.fill(values, 0, n, initValue);
  }

  public void update(int idx, int value) {
    for ( ; idx < n; idx |= idx + 1) {
      values[idx] = merge(values[idx], value);
    }
  }

  public int calc(int idx, int initValue) {
    int res = initValue;
    for ( ; idx >= 0; idx = (idx & (idx + 1)) - 1) {
      res = merge(res, values[idx]);
    }
    return res;
  }
}
