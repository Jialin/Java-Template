package template.collections.deque;

import template.collections.queue.IntArrayQueue;

public class IntArrayDeque extends IntArrayQueue {

  public IntArrayDeque(int capacity) {
    super(capacity);
  }

  @Override
  public void add(int value) {
    throw new UnsupportedOperationException();
  }

  public void addFirst(int value) {
    ensureCapacity(size() + 1);
    open = (open - 1) & (values.length - 1);
    values[open] = value;
  }

  public void addLast(int value) {
    super.add(value);
  }

  @Override
  public int peek() {
    throw new UnsupportedOperationException();
  }

  public int peekFirst() {
    return super.peek();
  }

  public int peekLast() {
    if (open == close) throw new ArrayIndexOutOfBoundsException();
    return values[(close - 1) & (values.length - 1)];
  }

  @Override
  public int poll() {
    throw new UnsupportedOperationException();
  }

  public int pollFirst() {
    return super.poll();
  }

  public int pollLast() {
    if (open == close) throw new ArrayIndexOutOfBoundsException();
    close = (close - 1) & (values.length - 1);
    return values[close];
  }
}
