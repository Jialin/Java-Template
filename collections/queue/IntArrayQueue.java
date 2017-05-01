package template.collections.queue;

import template.collections.IntCollection;
import template.io.Displayable;

public class IntArrayQueue implements IntCollection, Displayable {

  private static int[] EMPTY = {};

  protected int[] values;
  protected int open, close;

  public IntArrayQueue() {
    values = EMPTY;
    clear();
  }

  public IntArrayQueue(int capacity) {
    values = new int[Integer.highestOneBit(capacity) << 1];
    clear();
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
  public void add(int value) {
    ensureCapacity(size() + 1);
    values[close++] = value;
    close &= values.length - 1;
  }

  public int peek() {
    if (open == close) throw new ArrayIndexOutOfBoundsException();
    return values[open];
  }

  public int poll() {
    if (open == close) throw new ArrayIndexOutOfBoundsException();
    int res = values[open];
    open = (open + 1) & (values.length - 1);
    return res;
  }

  @Override
  public String toDisplay() {
    StringBuilder sb = new StringBuilder("[");
    for (int i = open; i != close; i = (i + 1) & (values.length - 1)) {
      if (i != open) sb.append(',').append(' ');
      sb.append(values[i]);
    }
    return sb.append(']').toString();
  }

  protected void ensureCapacity(int capacity) {
    if (capacity < values.length) return;
    int[] newValues = new int[Integer.highestOneBit(capacity) << 1];
    for (int i = 0, j = open; j != close; ++i, j = (j + 1) & (values.length - 1)) {
      newValues[i] = values[j];
    }
    close = size();
    open = 0;
    values = newValues;
  }
}
