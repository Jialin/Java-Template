package template.numbertheory.matrix;

import template.collections.bitset.BitSet;
import template.numbertheory.number.IntUtils;

public class Matrix01 {

  public BitSet[] row;

  private int n;

  public Matrix01(int n) {
    init(n);
  }

  public Matrix01(Matrix01 o) {
    init(o);
  }

  public static Matrix01 mul(Matrix01 l, Matrix01 r) {
    Matrix01 res = new Matrix01(l.n);
    res.initMul(l, r);
    return res;
  }

  public void init(int n) {
    this.n = n;
    ensureCapacity(n);
    for (int i = 0; i < n; ++i) {
      row[i].clear();
    }
  }

  public void initEye(int n) {
    init(n);
    for (int i = 0; i < n; ++i) {
      row[i].set(i);
    }
  }

  public void init(Matrix01 o) {
    init(o.n);
    for (int i = 0; i < n; ++i) {
      row[i].init(o.row[i]);
    }
  }

  /** this = l * r */
  public void initMul(Matrix01 l, Matrix01 r) {
    if (l.n != r.n) throw new IllegalArgumentException();
    init(l.n);
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) if (l.row[i].get(j)) {
        row[i].or(r.row[j]);
      }
    }
  }

  public void set(int x, int y) {
    row[x].set(y);
  }

  public void clear(int x, int y) {
    row[x].clear(y);
  }

  public void set(int x, int y, boolean value) {
    if (value) {
      set(x, y);
    } else {
      clear(x, y);
    }
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; ++i) {
      sb.append(row[i]);
      if (i + 1 != n) {
        sb.append('\n');
      }
    }
    return sb.toString();
  }

  private void ensureCapacity(int capacity) {
    if (row != null && row.length >= capacity) return;
    int n = IntUtils.nextPow2(capacity);
    row = new BitSet[n];
    for (int i = 0; i < n; ++i) {
      row[i] = new BitSet(n);
    }
  }
}
