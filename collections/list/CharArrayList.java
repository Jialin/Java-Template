package template.collections.list;

import template.array.CharArrayUtils;
import template.collections.CharCollection;

public class CharArrayList implements CharCollection {

  private static char[] EMPTY = {};

  public char[] values;
  public int size;

  public CharArrayList() {
    values = EMPTY;
    clear();
  }

  public CharArrayList(int capacity) {
    values = new char[Integer.highestOneBit(capacity) << 1];
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
  public void add(char value) {
    ensureCapacity(size() + 1);
    values[size++] = value;
  }

  public char get(int idx) {
    if (idx >= size) throw new ArrayIndexOutOfBoundsException();
    return values[idx];
  }

  public void set(int idx, char value) {
    if (idx >= size) throw new ArrayIndexOutOfBoundsException();
    values[idx] = value;
  }

  @Override
  public String toString() {
    return CharArrayUtils.toString(values, 0, size);
  }

  protected void ensureCapacity(int capacity) {
    if (capacity < values.length) return;
    char[] newValues = new char[Integer.highestOneBit(capacity) << 1];
    for (int i = 0; i < values.length; ++i) {
      newValues[i] = values[i];
    }
    size = size();
    values = newValues;
  }
}
