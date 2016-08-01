package template.collections.deque;

import template.collections.queue.IntArrayQueue;

public class IntArrayDeque extends IntArrayQueue {

  public IntArrayDeque(int capacity) {
    super(capacity);
  }

  public void addFirst(int v) {
    ensureCapacity(size() + 1);
    open = (open - 1) & (data.length - 1);
    data[open] = v;
  }

  public void addLast(int v) {
    add(v);
  }

  public int peekFirst() {
    return peek();
  }

  public int peekLast() {
    assert(open != close);
    return data[(close - 1) & (data.length - 1)];
  }

  public int pollFirst() {
    return poll();
  }

  public int pollLast() {
    assert(open != close);
    close = (close - 1) & (data.length - 1);
    return data[close];
  }
}
