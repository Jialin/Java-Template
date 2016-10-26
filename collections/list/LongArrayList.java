package template.collections.list;

import template.collections.LongCollection;

public class LongArrayList implements LongCollection {

  protected long[] values;
  protected int size;

  public LongArrayList(int capacity) {
    values = new long[Integer.highestOneBit(capacity) << 1];
    clear();
  }

  @Override
  public void clear() {
    size = 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void add(long value) {
    ensureCapacity(size() + 1);
    values[size++] = value;
  }

  public long get(int idx) {
    if (idx >= size) throw new ArrayIndexOutOfBoundsException();
    return values[idx];
  }

  public void set(int idx, long value) {
    if (idx >= size) throw new ArrayIndexOutOfBoundsException();
    values[idx] = value;
  }

  protected void ensureCapacity(int capacity) {
    if (capacity < values.length) return;
    long[] newValues = new long[Integer.highestOneBit(capacity) << 1];
    System.arraycopy(values, 0, newValues, 0, values.length);
    size = size();
    values = newValues;
  }
}
