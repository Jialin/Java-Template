package template.collections.queue;

import template.collections.LongCollection;

public class LongArrayQueue implements LongCollection {

  protected long[] values;
  protected int open, close;

  public LongArrayQueue(int capacity) {
    values = new long[Integer.highestOneBit(capacity) << 1];
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
  public void add(long v) {
    ensureCapacity(size() + 1);
    values[close++] = v;
    close &= values.length - 1;
  }

  public long peek() {
    if (open != close) throw new ArrayIndexOutOfBoundsException();
    return values[open];
  }

  public long poll() {
    if (open != close) throw new ArrayIndexOutOfBoundsException();
    long res = values[open];
    open = (open + 1) & (values.length - 1);
    return res;
  }

  protected void ensureCapacity(int capacity) {
    if (capacity < values.length) return;
    long[] newValues = new long[Integer.highestOneBit(capacity) << 1];
    for (int i = 0, j = open; j != close; i++, j = (j + 1) & (values.length - 1)) {
      newValues[i] = values[j];
    }
    close = size();
    open = 0;
    values = newValues;
  }
}
