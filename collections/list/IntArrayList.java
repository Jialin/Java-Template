package template.collections.list;

import template.collections.IntCollection;

public class IntArrayList implements IntCollection {

  private static int[] EMPTY = {};

  public int[] values;
  public int size;

  public IntArrayList() {
    values = EMPTY;
    clear();
  }

  public IntArrayList(int capacity) {
    values = new int[Integer.highestOneBit(capacity) << 1];
    clear();
  }

  @Override
  public void clear() {
    size = 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void add(int value) {
    ensureCapacity(size() + 1);
    values[size++] = value;
  }

  public int get(int idx) {
    if (idx >= size) throw new ArrayIndexOutOfBoundsException();
    return values[idx];
  }

  public void set(int idx, int value) {
    if (idx >= size) throw new ArrayIndexOutOfBoundsException();
    values[idx] = value;
  }

  protected void ensureCapacity(int capacity) {
    if (capacity < values.length) return;
    int[] newValues = new int[Integer.highestOneBit(capacity) << 1];
    for (int i = 0; i < values.length; ++i) {
      newValues[i] = values[i];
    }
    size = size();
    values = newValues;
  }
}
