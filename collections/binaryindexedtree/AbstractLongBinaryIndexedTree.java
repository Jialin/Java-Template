package template.collections.binaryindexedtree;

import java.util.Arrays;

public abstract class AbstractLongBinaryIndexedTree {

  private int n;
  private long[] values;

  public AbstractLongBinaryIndexedTree(int capacity) {
    values = new long[capacity];
  }

  public abstract long merge(long x, long y);

  public void init(int n, long initValue) {
    this.n = n;
    Arrays.fill(values, 0, n, initValue);
  }

  public void update(int idx, long value) {
    for ( ; idx < n; idx |= idx + 1) {
      values[idx] = merge(values[idx], value);
    }
  }

  public long calc(int idx, long initValue) {
    long res = initValue;
    for ( ; idx >= 0; idx = (idx & (idx + 1)) - 1) {
      res = merge(res, values[idx]);
    }
    return res;
  }
}
