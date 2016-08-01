package template.collections.queue;

import template.collections.IntCollection;

public class IntArrayQueue implements IntCollection {

  protected int[] data;
  protected int open, close;

  public IntArrayQueue(int capacity) {
    data = new int[Integer.highestOneBit(capacity) << 1];
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
  public void add(int v) {
    ensureCapacity(size() + 1);
    data[close++] = v;
    close &= data.length - 1;
  }

  public int peek() {
    assert(open != close);
    return data[open];
  }

  public int poll() {
    assert(open != close);
    int res = data[open];
    open = (open + 1) & (data.length - 1);
    return res;
  }

  protected void ensureCapacity(int capacity) {
    if (capacity < data.length) return;
    int[] newData = new int[Integer.highestOneBit(capacity) << 1];
    for (int i = 0, j = open; j != close; i++, j = (j + 1) & (data.length - 1)) {
      newData[i] = data[j];
    }
    close = size();
    open = 0;
    data = newData;
  }
}
