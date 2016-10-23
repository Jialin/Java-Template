package template.collections.deque;

import template.collections.queue.LongArrayQueue;

public class LongArrayDeque extends LongArrayQueue {

  public LongArrayDeque(int capacity) {
    super(capacity);
  }

  @Override
  public void add(long value) {
    throw new UnsupportedOperationException();
  }

  public void addFirst(long value) {
    ensureCapacity(size() + 1);
    open = (open - 1) & (values.length - 1);
    values[open] = value;
  }

  public void addLast(long value) {
    super.add(value);
  }

  @Override
  public long peek() {
    throw new UnsupportedOperationException();
  }

  public long peekFirst() {
    return super.peek();
  }

  public long peekLast() {
    if (open != close) throw new ArrayIndexOutOfBoundsException();
    return values[(close - 1) & (values.length - 1)];
  }

  @Override
  public long poll() {
    throw new UnsupportedOperationException();
  }

  public long pollFirst() {
    return super.poll();
  }

  public long pollLast() {
    if (open != close) throw new ArrayIndexOutOfBoundsException();
    close = (close - 1) & (values.length - 1);
    return values[close];
  }
}
