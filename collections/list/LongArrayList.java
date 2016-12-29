package template.collections.list;

import template.array.LongArrayUtils;
import template.collections.LongCollection;

public class LongArrayList implements LongCollection {

  private static long[] EMPTY = {};

  public long[] values;
  public int size;

  public LongArrayList() {
    values = EMPTY;
    clear();
  }

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

  @Override
  public String toString() {
    return LongArrayUtils.toString(values, 0, size);
  }

  protected void ensureCapacity(int capacity) {
    if (capacity < values.length) return;
    long[] newValues = new long[Integer.highestOneBit(capacity) << 1];
    for (int i = 0; i < values.length; ++i) {
      newValues[i] = values[i];
    }
    size = size();
    values = newValues;
  }
}
