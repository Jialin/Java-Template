package template.collections.list;

import template.collections.LongCollection;

public class LongArrayList implements LongCollection {

  private long[] values;
  private int close;

  public LongArrayList(int capacity) {
    values = new long[Integer.highestOneBit(capacity) << 1];
  }

  @Override
  public void clear() {
    close = 0;
  }

  @Override
  public int size() {
    return close;
  }

  @Override
  public void add(long v) {
    ensureCapacity(size() + 1);
    values[close++] = v;
  }

  public long get(int idx) {
    if (idx >= close) throw new ArrayIndexOutOfBoundsException();
    return values[idx];
  }

  public void set(int idx, long value) {
    if (idx >= close) throw new ArrayIndexOutOfBoundsException();
    values[idx] = value;
  }

  protected void ensureCapacity(int capacity) {
    if (capacity < values.length) return;
    long[] newValues = new long[Integer.highestOneBit(capacity) << 1];
    System.arraycopy(values, 0, newValues, 0, values.length);
    close = size();
    values = newValues;
  }
}
