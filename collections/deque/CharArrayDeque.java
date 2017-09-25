package template.collections.deque;

import template.collections.queue.CharArrayQueue;

public class CharArrayDeque extends CharArrayQueue {

  public CharArrayDeque(int capacity) {
    super(capacity);
  }

  @Override
  public void add(char value) {
    throw new UnsupportedOperationException();
  }

  public void addFirst(char value) {
    ensureCapacity(size() + 1);
    open = (open - 1) & (values.length - 1);
    values[open] = value;
  }

  public void addLast(char value) {
    super.add(value);
  }

  @Override
  public char peek() {
    throw new UnsupportedOperationException();
  }

  public char peekFirst() {
    return super.peek();
  }

  public char peekLast() {
    if (open == close) throw new ArrayIndexOutOfBoundsException();
    return values[(close - 1) & (values.length - 1)];
  }

  @Override
  public char poll() {
    throw new UnsupportedOperationException();
  }

  public char pollFirst() {
    return super.poll();
  }

  public char pollLast() {
    if (open == close) throw new ArrayIndexOutOfBoundsException();
    close = (close - 1) & (values.length - 1);
    return values[close];
  }
}
