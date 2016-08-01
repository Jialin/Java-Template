package template.collections.queue;

import template.collections.LongCollection;

public class LongArrayQueue implements LongCollection {

  protected long[] data;
  protected int open, close;

  public LongArrayQueue(int capacity) {
    data = new long[Integer.highestOneBit(capacity) << 1];
  }

  @Override
  public void clear() {
    open = close = 0;
  }

  @Override
  public int size() {
    return (close - open) & (data.length - 1);
  }

  @Override
  public void add(long v) {
    ensureCapacity(size() + 1);
    data[close++] = v;
    close &= data.length - 1;
  }

  public long peek() {
    assert(open != close);
    return data[open];
  }

  public long poll() {
    assert(open != close);
    long res = data[open];
    open = (open + 1) & (data.length - 1);
    return res;
  }

  protected void ensureCapacity(int capacity) {
    if (capacity < data.length) return;
    long[] newData = new long[Integer.highestOneBit(capacity) << 1];
    for (int i = 0, j = open; j != close; i++, j = (j + 1) & (data.length - 1)) {
      newData[i] = data[j];
    }
    close = size();
    open = 0;
    data = newData;
  }
}
