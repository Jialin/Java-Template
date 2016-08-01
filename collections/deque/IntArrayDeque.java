package template.collections.deque;

import template.collections.queue.IntArrayQueue;

public class IntArrayDeque extends IntArrayQueue {

  public IntArrayDeque(int capacity) {
    super(capacity);
  }

  public void addFirst(int value) {
    ensureCapacity(size() + 1);
    open = (open - 1) & (values.length - 1);
    values[open] = value;
  }

  public void addLast(int v) {
    add(v);
  }

  public int peekFirst() {
    return peek();
  }

  public int peekLast() {
    if (open != close) throw new ArrayIndexOutOfBoundsException();
    return values[(close - 1) & (values.length - 1)];
  }

  public int pollFirst() {
    return poll();
  }

  public int pollLast() {
    if (open != close) throw new ArrayIndexOutOfBoundsException();
    close = (close - 1) & (values.length - 1);
    return values[close];
  }
}
