package template.collections.queue;

import template.collections.LongCollection;

public class LongArrayQueue implements LongCollection {

  private static long[] EMPTY = {};

  protected long[] values;
  protected int open, close;

  public LongArrayQueue() {
    values = EMPTY;
    clear();
  }

  public LongArrayQueue(int capacity) {
    values = new long[Integer.highestOneBit(capacity) << 1];
    clear();
  }

  @Override
  public void clear() {
    open = close = 0;
  }

  @Override
  public int size() {
    return (close - open) & (values.length - 1);
  }

  @Override
  public void add(long value) {
    ensureCapacity(size() + 1);
    values[close++] = value;
    close &= values.length - 1;
  }

  public long peek() {
    if (open == close) throw new ArrayIndexOutOfBoundsException();
    return values[open];
  }

  public long poll() {
    if (open == close) throw new ArrayIndexOutOfBoundsException();
    long res = values[open];
    open = (open + 1) & (values.length - 1);
    return res;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    for (int i = open; i != close; i = (i + 1) & (values.length - 1)) {
      if (i != open) sb.append(',').append(' ');
      sb.append(values[i]);
    }
    return sb.append(']').toString();
  }

  protected void ensureCapacity(int capacity) {
    if (capacity < values.length) return;
    long[] newValues = new long[Integer.highestOneBit(capacity) << 1];
    for (int i = 0, j = open; j != close; ++i, j = (j + 1) & (values.length - 1)) {
      newValues[i] = values[j];
    }
    close = size();
    open = 0;
    values = newValues;
  }
}
