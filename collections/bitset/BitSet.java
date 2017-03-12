package template.collections.bitset;

import template.numbertheory.number.IntUtils;

import java.util.Arrays;

public class BitSet {

  public int n;

  private int m;
  private long[] value, tmpValue;

  public BitSet(int capacity) {
    init(capacity);
  }

  public BitSet(BitSet o) {
    init(o);
  }

  public void init(int n) {
    this.n = n;
    m = (n + 63) >> 6;
    ensureCapacity(m);
    clear();
  }

  public void init(BitSet o) {
    init(o.n);
    for (int i = 0; i < m; ++i) {
      value[i] = o.value[i];
    }
  }

  public void clear() {
    Arrays.fill(value, 0, m, 0);
  }

  public void set(int bit) {
    value[bit >> 6] |= 1L << (bit & 63);
  }

  public void clear(int bit) {
    value[bit >> 6] &= ~(1L << (bit & 63));
  }

  public void set(int bit, boolean value) {
    if (value) {
      set(bit);
    } else {
      clear(bit);
    }
  }

  public boolean get(int bit) {
    return ((value[bit >> 6] >>> (bit & 63)) & 1) > 0;
  }

  public int cardinality() {
    int res = 0;
    for (int i = 0; i < m; ++i) {
      res += Long.bitCount(value[i]);
    }
    return res;
  }

  public boolean intersects(BitSet o) {
    for (int i = 0; i < m; ++i) {
      if ((value[i] & o.value[i]) != 0) return true;
    }
    return false;
  }

  /** this |= o */
  public void or(BitSet o) {
    for (int i = 0; i < m; ++i) {
      value[i] |= o.value[i];
    }
  }

  /** this <<= shift */
  public void shiftLeft(int shift) {
    shiftLeftInternal(shift);
    for (int i = 0; i < m; ++i) {
      value[i] = tmpValue[i];
    }
  }

  /** this |= this << shift */
  public void orShiftLeft(int shift) {
    shiftLeftInternal(shift);
    for (int i = 0; i < m; ++i) {
      value[i] |= tmpValue[i];
    }
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer(n);
    for (int i = 0; i < n; ++i) {
      sb.append(get(i) ? '1' : '0');
    }
    return sb.toString();
  }

  private void shiftLeftInternal(int shift) {
    if (shift < 0) throw new IllegalArgumentException();
    Arrays.fill(tmpValue, 0);
    int x = shift >> 6, y = shift & 63;
    for (int i = m - x - 1; i >= 0; --i) {
      if (y > 0 && i + x + 1 < m) {
        tmpValue[i + x + 1] |= value[i] >>> (64 - y);
      }
      tmpValue[i + x] |= value[i] << y;
    }
  }

  private void ensureCapacity(int capacity) {
    if (value != null && value.length >= capacity) return;
    int n = IntUtils.nextPow2(capacity);
    value = new long[n];
    tmpValue = new long[n];
  }
}
