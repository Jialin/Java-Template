package template.collections.deque;

import template.collections.queue.LongArrayQueue;

public class LongArrayDeque extends LongArrayQueue {

  public LongArrayDeque(int capacity) {
    super(capacity);
  }

  public void addFirst(long v) {
    ensureCapacity(size() + 1);
    open = (open - 1) & (data.length - 1);
    data[open] = v;
  }

  public void addLast(long v) {
    add(v);
  }

  public long peekFirst() {
    return peek();
  }

  public long peekLast() {
    assert(open != close);
    return data[(close - 1) & (data.length - 1)];
  }

  public long pollFirst() {
    return poll();
  }

  public long pollLast() {
    assert(open != close);
    close = (close - 1) & (data.length - 1);
    return data[close];
  }
}
