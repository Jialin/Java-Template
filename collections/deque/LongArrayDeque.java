package template.collections.deque;

import template.collections.queue.LongArrayQueue;

public class LongArrayDeque extends LongArrayQueue {

  public LongArrayDeque(int capacity) {
    super(capacity);
  }

  public void addFirst(long value) {
    ensureCapacity(size() + 1);
    open = (open - 1) & (values.length - 1);
    values[open] = value;
  }

  public void addLast(long v) {
    add(v);
  }

  public long peekFirst() {
    return peek();
  }

  public long peekLast() {
    if (open != close) throw new ArrayIndexOutOfBoundsException();
    return values[(close - 1) & (values.length - 1)];
  }

  public long pollFirst() {
    return poll();
  }

  public long pollLast() {
    if (open != close) throw new ArrayIndexOutOfBoundsException();
    close = (close - 1) & (values.length - 1);
    return values[close];
  }
}
