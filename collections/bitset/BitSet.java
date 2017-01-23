package template.collections.bitset;

import java.util.Arrays;

public class BitSet {

  private int n, m;
  private long[] value, tmpValue;

  public BitSet(int n) {
    this.n = n;
    m = (n + 63) >> 6;
    value = new long[m];
    tmpValue = new long[m];
  }

  public void clear() {
    Arrays.fill(value, 0);
  }

  public void set(int bit) {
    value[bit >> 6] |= 1L << (bit & 63);
  }

  public boolean get(int bit) {
    return ((value[bit >> 6] >>> (bit & 63)) & 1) > 0;
  }

  /** this << shift */
  public void shiftLeft(int shift) {
    shiftLeftInternal(shift);
    for (int i = 0; i < m; ++i) {
      value[i] = tmpValue[i];
    }
  }

  /** this | (this << shift) */
  public void orShiftLeft(int shift) {
    shiftLeftInternal(shift);
    for (int i = 0; i < m; ++i) {
      value[i] |= tmpValue[i];
    }
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer(n);
    for (int i = n - 1; i >= 0; --i) {
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
}
