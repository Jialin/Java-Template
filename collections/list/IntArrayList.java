package template.collections.list;

import template.collections.IntCollection;

public class IntArrayList implements IntCollection {

  private int[] values;
  private int close;

  public IntArrayList(int capacity) {
    values = new int[Integer.highestOneBit(capacity) << 1];
  }

  @Override
  public void clear() {
    close = 0;
  }

  @Override
  public int size() {
    return close;
  }

  @Override
  public void add(int v) {
    ensureCapacity(size() + 1);
    values[close++] = v;
  }

  public int get(int idx) {
    if (idx >= close) throw new ArrayIndexOutOfBoundsException();
    return values[idx];
  }

  public void set(int idx, int value) {
    if (idx >= close) throw new ArrayIndexOutOfBoundsException();
    values[idx] = value;
  }

  protected void ensureCapacity(int capacity) {
    if (capacity < values.length) return;
    int[] newValues = new int[Integer.highestOneBit(capacity) << 1];
    System.arraycopy(values, 0, newValues, 0, values.length);
    close = size();
    values = newValues;
  }
}
